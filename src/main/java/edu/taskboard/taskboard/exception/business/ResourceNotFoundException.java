package edu.taskboard.taskboard.exception.business;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class ResourceNotFoundException extends CustomException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(ErrorCode.RESOURCE_NOT_FOUND,
                "%s not found with id: %s".formatted(resourceName, id));
    }
}