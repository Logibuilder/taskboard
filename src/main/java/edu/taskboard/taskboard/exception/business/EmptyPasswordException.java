package edu.taskboard.taskboard.exception.business;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class EmptyPasswordException extends CustomException {
    public EmptyPasswordException() {
        super(ErrorCode.EMPTY_PASSWORD,
                "Password cannot be empty or null");
    }
}