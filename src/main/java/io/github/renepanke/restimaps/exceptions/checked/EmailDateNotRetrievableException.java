package io.github.renepanke.restimaps.exceptions.checked;

public class EmailDateNotRetrievableException extends RestImapException{
    public EmailDateNotRetrievableException() {
    }

    public EmailDateNotRetrievableException(String message) {
        super(message);
    }

    public EmailDateNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailDateNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailDateNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
