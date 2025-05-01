package io.github.renepanke.restimaps.exceptions.checked;

public class AttachmentDataSourceInputStreamNotRetrievable extends RestImapException{
    public AttachmentDataSourceInputStreamNotRetrievable() {
    }

    public AttachmentDataSourceInputStreamNotRetrievable(String message) {
        super(message);
    }

    public AttachmentDataSourceInputStreamNotRetrievable(String message, Throwable cause) {
        super(message, cause);
    }

    public AttachmentDataSourceInputStreamNotRetrievable(Throwable cause) {
        super(cause);
    }

    public AttachmentDataSourceInputStreamNotRetrievable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
