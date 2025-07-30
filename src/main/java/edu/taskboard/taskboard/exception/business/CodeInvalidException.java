package edu.taskboard.taskboard.exception.business;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class CodeInvalidException extends CustomException {
    public CodeInvalidException() {
        super(ErrorCode.INVALID_CODE, "The validation code is incorrect or does not exist.");
    }
}
