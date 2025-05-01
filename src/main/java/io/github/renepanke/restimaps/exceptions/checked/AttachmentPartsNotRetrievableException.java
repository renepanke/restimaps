package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentPartsNotRetrievableException extends RestImapException {
    public AttachmentPartsNotRetrievableException() {
    }

    public AttachmentPartsNotRetrievableException(String message) {
        super(message);
    }

    public AttachmentPartsNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentPartsNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public AttachmentPartsNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
