package edu.taskboard.taskboard.exception;

import org.springframework.http.HttpStatus;

import java.util.Arrays;

/**
 * Centralized application error codes catalog
 * Combines custom business codes with HTTP standards
 */
public enum ErrorCode {
    // 4xx - Client Errors (1000-1999)
    BAD_REQUEST(1000, HttpStatus.BAD_REQUEST, "Invalid request"),
    UNAUTHORIZED(1001, HttpStatus.UNAUTHORIZED, "Authentication required"),
    FORBIDDEN(1002, HttpStatus.FORBIDDEN, "Access denied"),
    RESOURCE_NOT_FOUND(1003, HttpStatus.NOT_FOUND, "Resource not found"),
    METHOD_NOT_ALLOWED(1004, HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not allowed"),
    DUPLICATE_RESOURCE(1005, HttpStatus.CONFLICT, "Resource already exists"),
    VALIDATION_FAILED(1006, HttpStatus.UNPROCESSABLE_ENTITY, "Validation failed"),
    EMPTY_PASSWORD(1007, HttpStatus.BAD_REQUEST, "Password cannot be empty or null"),

    // 5xx - Server Errors (2000-2999)
    INTERNAL_ERROR(2000, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    SERVICE_UNAVAILABLE(2001, HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable"),

    // Business-specific (3000-3999)
    EXPIRED_TOKEN(3000, HttpStatus.UNAUTHORIZED, "Expired token"),
    INVALID_OPERATION(3001, HttpStatus.BAD_REQUEST, "Invalid operation"), // ou FORBIDDEN selon le cas
    EXPIRED_CODE(3002, HttpStatus.BAD_REQUEST, "Validation code expired"),
    INVALID_CODE(3003, HttpStatus.BAD_REQUEST, "Invalid validation code");


    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

    ErrorCode(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }

    // Getters
    public int getCode() { return code; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getDescription() { return description; }

    /**
     * Finds ErrorCode by numeric code
     * @throws IllegalArgumentException for unknown codes
     */
    public static ErrorCode fromCode(int code) {
        return Arrays.stream(values())
                .filter(e -> e.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown error code: " + code));
    }
}

