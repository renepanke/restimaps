package io.github.renepanke.restimaps.exceptions.checked;

public class UnsupportedAttachmentEncodingException extends RestImapException {
    public UnsupportedAttachmentEncodingException() {
    }

    public UnsupportedAttachmentEncodingException(String message) {
        super(message);
    }

    public UnsupportedAttachmentEncodingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedAttachmentEncodingException(Throwable cause) {
        super(cause);
    }

    public UnsupportedAttachmentEncodingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
