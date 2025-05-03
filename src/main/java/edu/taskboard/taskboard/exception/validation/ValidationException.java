package edu.taskboard.taskboard.exception.validation;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class ValidationException extends CustomException {
    public ValidationException(String field, String message) {
        super(ErrorCode.VALIDATION_FAILED,
                "Validation failed for field %s: %s".formatted(field, message));
    }
}


