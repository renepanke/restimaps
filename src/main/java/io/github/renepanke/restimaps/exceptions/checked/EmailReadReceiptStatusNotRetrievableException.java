package io.github.renepanke.restimaps.exceptions.checked;

public class EmailReadReceiptStatusNotRetrievableException extends RestImapException {
    public EmailReadReceiptStatusNotRetrievableException() {
    }

    public EmailReadReceiptStatusNotRetrievableException(String message) {
        super(message);
    }

    public EmailReadReceiptStatusNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailReadReceiptStatusNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public EmailReadReceiptStatusNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
