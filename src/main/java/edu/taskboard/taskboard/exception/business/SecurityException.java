package edu.taskboard.taskboard.exception.business;

import edu.taskboard.taskboard.exception.CustomException;
import edu.taskboard.taskboard.exception.ErrorCode;

public class SecurityException extends CustomException {

    // For basic authentication (not logged in)
    public SecurityException() {
        super(ErrorCode.UNAUTHORIZED, "Authentication required");
    }

    // For expired token
    public static SecurityException tokenExpired() {
        return new SecurityException(ErrorCode.EXPIRED_TOKEN, "Token has expired");
    }

    // For unauthorized access (forbidden)
    public static SecurityException accessDenied() {
        return new SecurityException(ErrorCode.FORBIDDEN, "Access denied");
    }

    // Private constructor for factory methods
    private SecurityException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}
