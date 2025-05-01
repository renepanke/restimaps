package io.github.renepanke.restimaps.exceptions.checked;

public class FolderNotFoundException extends RestImapException {
    public FolderNotFoundException() {
    }

    public FolderNotFoundException(String message) {
        super(message);
    }

    public FolderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FolderNotFoundException(Throwable cause) {
        super(cause);
    }

    public FolderNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
