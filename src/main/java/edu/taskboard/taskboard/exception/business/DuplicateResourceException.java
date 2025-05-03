package edu.taskboard.taskboard.exception.business;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class DuplicateResourceException extends CustomException {
    public DuplicateResourceException(String field, String value) {
        super(ErrorCode.DUPLICATE_RESOURCE,
                "%s '%s' already exists".formatted(field, value));
    }
}