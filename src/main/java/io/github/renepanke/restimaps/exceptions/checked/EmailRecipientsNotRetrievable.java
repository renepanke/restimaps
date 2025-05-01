package io.github.renepanke.restimaps.exceptions.checked;

public class EmailRecipientsNotRetrievable extends RestImapException{
    public EmailRecipientsNotRetrievable() {
    }

    public EmailRecipientsNotRetrievable(String message) {
        super(message);
    }

    public EmailRecipientsNotRetrievable(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailRecipientsNotRetrievable(Throwable cause) {
        super(cause);
    }

    public EmailRecipientsNotRetrievable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
