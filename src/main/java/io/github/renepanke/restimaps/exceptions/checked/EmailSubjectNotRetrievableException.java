package io.github.renepanke.restimaps.exceptions.checked;

public class EmailSubjectNotRetrievableException extends RestImapException {
    public EmailSubjectNotRetrievableException() {
    }

    public EmailSubjectNotRetrievableException(String message) {
        super(message);
    }

    public EmailSubjectNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailSubjectNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailSubjectNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
