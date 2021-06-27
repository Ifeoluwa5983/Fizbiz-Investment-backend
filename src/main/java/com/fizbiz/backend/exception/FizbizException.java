package com.fizbiz.backend.exception;

public class FizbizException extends Exception{
    public FizbizException() {
        super();
    }

    public FizbizException(String message) {
        super(message);
    }

    public FizbizException(String message, Throwable cause) {
        super(message, cause);
    }

    public FizbizException(Throwable cause) {
        super(cause);
    }

    protected FizbizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
