package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentSizeNotRetrievable extends RestImapException{
    public AttachmentSizeNotRetrievable() {
    }

    public AttachmentSizeNotRetrievable(String message) {
        super(message);
    }

    public AttachmentSizeNotRetrievable(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentSizeNotRetrievable(Throwable cause) {
        super(cause);
    }

    public AttachmentSizeNotRetrievable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
