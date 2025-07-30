package edu.taskboard.taskboard.exception.business;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class CodeFormatException extends CustomException {

    public CodeFormatException() {
            super(ErrorCode.INVALID_CODE, "Invalid code format. The code must be exactly 6 digits.");
        }
}
