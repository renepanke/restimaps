package io.github.renepanke.restimaps.exceptions.checked;

public class EmailsNotRetrievableException extends Exception {

    public EmailsNotRetrievableException() {
    }

    public EmailsNotRetrievableException(String message) {
        super(message);
    }

    public EmailsNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailsNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailsNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
