package edu.taskboard.taskboard.exception.business;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class CodeExpiredException extends CustomException {

    public CodeExpiredException() {
        super(ErrorCode.EXPIRED_CODE, "The validation code has expired.");
    }
}
