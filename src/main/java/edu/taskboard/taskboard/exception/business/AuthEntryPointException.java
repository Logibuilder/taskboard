package edu.taskboard.taskboard.exception.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.taskboard.taskboard.exception.ApiError;
import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthEntryPointException implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Autowired
    public AuthEntryPointException(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }



    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        SecurityException securityException;

        // Déterminer le type d'erreur de sécurité en fonction du message d'exception
        if (authException.getMessage() != null && authException.getMessage().contains("expired")) {
            securityException = SecurityException.tokenExpired();
        } else if (authException.getMessage() != null && authException.getMessage().contains("forbidden")) {
            securityException = SecurityException.accessDenied();
        } else {
            securityException = new SecurityException(); // Utilise UNAUTHORIZED (1001) par défaut
        }

        ErrorCode errorCode = securityException.getErrorCode();

        // Construire l'erreur API
        ApiError apiError = ApiError.builder()
                .timestamp(Instant.now())
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().getReasonPhrase())
                .code(errorCode.getCode())
                .message(errorCode.getDescription())
                .path(request.getRequestURI())
                .validationErrors(null)
                .build();

        // Configurer la réponse
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(apiError));



    }
}
