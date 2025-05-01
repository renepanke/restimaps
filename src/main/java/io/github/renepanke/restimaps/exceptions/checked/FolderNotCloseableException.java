package io.github.renepanke.restimaps.exceptions.checked;

public class FolderNotCloseableException extends RestImapException{
    public FolderNotCloseableException() {
    }

    public FolderNotCloseableException(String message) {
        super(message);
    }

    public FolderNotCloseableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNotCloseableException(Throwable cause) {
        super(cause);
    }

    public FolderNotCloseableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
