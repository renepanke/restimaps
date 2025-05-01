package io.github.renepanke.restimaps.exceptions.checked;

public class EmailFromNotRetrievable extends RestImapException {
    public EmailFromNotRetrievable() {
    }

    public EmailFromNotRetrievable(String message) {
        super(message);
    }

    public EmailFromNotRetrievable(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailFromNotRetrievable(Throwable cause) {
        super(cause);
    }

    public EmailFromNotRetrievable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
