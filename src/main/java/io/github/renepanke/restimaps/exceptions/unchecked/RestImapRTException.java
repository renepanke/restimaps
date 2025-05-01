package io.github.renepanke.restimaps.exceptions.unchecked;

public class RestImapRTException extends RuntimeException {
    public RestImapRTException() {
    }

    public RestImapRTException(String message) {
        super(message);
    }

    public RestImapRTException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestImapRTException(Throwable cause) {
        super(cause);
    }

    public RestImapRTException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
