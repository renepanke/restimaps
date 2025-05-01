package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentNotFoundException extends RestImapException{
    public AttachmentNotFoundException() {
    }

    public AttachmentNotFoundException(String message) {
        super(message);
    }

    public AttachmentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentNotFoundException(Throwable cause) {
        super(cause);
    }

    public AttachmentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
