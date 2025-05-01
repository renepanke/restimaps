package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentContentIdNotRetrievableException extends RestImapException{
    public AttachmentContentIdNotRetrievableException() {
    }

    public AttachmentContentIdNotRetrievableException(String message) {
        super(message);
    }

    public AttachmentContentIdNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentContentIdNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public AttachmentContentIdNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
