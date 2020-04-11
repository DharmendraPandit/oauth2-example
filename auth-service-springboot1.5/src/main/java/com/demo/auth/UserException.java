package com.demo.auth;

/**
 * @author Dharmendra Pandit
 */
public class UserException extends RuntimeException {

    private final int status;
    private final String message;
    private String errorCode;

    public UserException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public UserException(int status, String message, String errorCode) {
        super(message);
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
