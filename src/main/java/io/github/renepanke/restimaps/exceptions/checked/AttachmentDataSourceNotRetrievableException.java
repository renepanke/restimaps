package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentDataSourceNotRetrievableException extends RestImapException {
    public AttachmentDataSourceNotRetrievableException() {
    }

    public AttachmentDataSourceNotRetrievableException(String message) {
        super(message);
    }

    public AttachmentDataSourceNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentDataSourceNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public AttachmentDataSourceNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
