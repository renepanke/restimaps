package io.github.renepanke.restimaps.exceptions.checked;

public class EmailSenderNotRetrievableException extends RestImapException{
    public EmailSenderNotRetrievableException() {
    }

    public EmailSenderNotRetrievableException(String message) {
        super(message);
    }

    public EmailSenderNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSenderNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailSenderNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
