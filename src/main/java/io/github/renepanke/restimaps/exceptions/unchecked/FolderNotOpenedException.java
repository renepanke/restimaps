package io.github.renepanke.restimaps.exceptions.unchecked;

public class FolderNotOpenedException extends RestImapRTException {
    public FolderNotOpenedException() {
    }

    public FolderNotOpenedException(String message) {
        super(message);
    }

    public FolderNotOpenedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNotOpenedException(Throwable cause) {
        super(cause);
    }

    public FolderNotOpenedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
