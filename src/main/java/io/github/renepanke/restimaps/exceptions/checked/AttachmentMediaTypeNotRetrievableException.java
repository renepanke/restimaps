package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentMediaTypeNotRetrievableException extends RestImapException {
    public AttachmentMediaTypeNotRetrievableException() {
    }

    public AttachmentMediaTypeNotRetrievableException(String message) {
        super(message);
    }

    public AttachmentMediaTypeNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentMediaTypeNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public AttachmentMediaTypeNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
