package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentNameNotRetrievableException extends RestImapException {
    public AttachmentNameNotRetrievableException() {
    }

    public AttachmentNameNotRetrievableException(String message) {
        super(message);
    }

    public AttachmentNameNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentNameNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public AttachmentNameNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
