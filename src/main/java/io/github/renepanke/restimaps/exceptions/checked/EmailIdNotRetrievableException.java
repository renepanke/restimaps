package io.github.renepanke.restimaps.exceptions.checked;

public class EmailIdNotRetrievableException extends RestImapException{
    public EmailIdNotRetrievableException() {
    }

    public EmailIdNotRetrievableException(String message) {
        super(message);
    }

    public EmailIdNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailIdNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailIdNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
