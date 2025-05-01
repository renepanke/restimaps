package io.github.renepanke.restimaps.exceptions.checked;

public class EmailContentNotRetrievableException extends RestImapException{
    public EmailContentNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public EmailContentNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailContentNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailContentNotRetrievableException(String message) {
        super(message);
    }

    public EmailContentNotRetrievableException() {
    }
}
