package io.github.renepanke.restimaps.exceptions.checked;

public class EmailReadStatusNotRetrievableException extends RestImapException{
    public EmailReadStatusNotRetrievableException() {
    }

    public EmailReadStatusNotRetrievableException(String message) {
        super(message);
    }

    public EmailReadStatusNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailReadStatusNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailReadStatusNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
