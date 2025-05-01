package io.github.renepanke.restimaps.exceptions.checked;

public class FolderNotOpenableException extends RestImapException {
    public FolderNotOpenableException() {
    }

    public FolderNotOpenableException(String message) {
        super(message);
    }

    public FolderNotOpenableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNotOpenableException(Throwable cause) {
        super(cause);
    }

    public FolderNotOpenableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
