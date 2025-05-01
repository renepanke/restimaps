package io.github.renepanke.restimaps.exceptions.checked;

public class EmailReplyToNotRetrievableException extends RestImapException {
    public EmailReplyToNotRetrievableException() {
    }

    public EmailReplyToNotRetrievableException(String message) {
        super(message);
    }

    public EmailReplyToNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailReplyToNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailReplyToNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
