package edu.taskboard.taskboard.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class ApiError {
    private final Instant timestamp;
    private final int status;         // Code HTTP numérique (ex: 400)
    private final String error;       // Description textuelle du statut (ex: "Bad Request")
    private final int code;           // Code d'erreur métier (pas ErrorCode enum)
    private final String message;     // Message détaillé
    private final String path;        // Chemin de la requête
    private final List<ValidationError> validationErrors;

    @Getter
    @Builder
    public static class ValidationError {
        private final String field;
        private final String message;
        private final Object rejectedValue;
    }
}