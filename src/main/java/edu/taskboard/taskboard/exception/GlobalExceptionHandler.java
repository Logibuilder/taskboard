package edu.taskboard.taskboard.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiError> handleCustomException(CustomException ex, WebRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        HttpStatus httpStatus = errorCode.getHttpStatus();
        String path = extractRequestPath(request);

        log.error("Business error occurred: [{}] {} - Path: {}",
                errorCode.getCode(),
                errorCode.getDescription(),
                path);

        ApiError apiError = ApiError.builder()
                .timestamp(Instant.now())
                .status(httpStatus.value())
                .error(httpStatus.getReasonPhrase())
                .code(errorCode.getCode())
                .message(ex.getMessage())
                .path(path)
                .build();

        return ResponseEntity.status(httpStatus).body(apiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {

        String path = extractRequestPath(request);

        List<ApiError.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> ApiError.ValidationError.builder()
                        .field(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .rejectedValue(fieldError.getRejectedValue())
                        .build())
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .code(ErrorCode.VALIDATION_FAILED.getCode())
                .message("Validation failed for one or more fields")
                .path(path)
                .validationErrors(validationErrors)
                .build();

        log.warn("Validation error occurred: {}", apiError);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception ex, WebRequest request) {
        String path = extractRequestPath(request);

        log.error("Unhandled exception occurred for path {}: ", path, ex);

        ApiError apiError = ApiError.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code(ErrorCode.INTERNAL_ERROR.getCode())
                .message("An unexpected error occurred")
                .path(path)
                .build();

        return ResponseEntity.internalServerError().body(apiError);
    }

    private String extractRequestPath(WebRequest request) {
        // Solution robuste pour extraire le chemin
        String description = request.getDescription(false);
        // Retire le préfixe "uri=" s'il existe
        return description.startsWith("uri=") ? description.substring(4) : description;
    }
}