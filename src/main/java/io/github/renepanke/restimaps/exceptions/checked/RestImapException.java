package io.github.renepanke.restimaps.exceptions.checked;

public class RestImapException extends Exception {

    public RestImapException() {
    }

    public RestImapException(String message) {
        super(message);
    }

    public RestImapException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestImapException(Throwable cause) {
        super(cause);
    }

    public RestImapException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
