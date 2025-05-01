package io.github.renepanke.restimaps.exceptions.checked;

public class FoldersNotRetrievableException extends RestImapException {
    public FoldersNotRetrievableException() {
    }

    public FoldersNotRetrievableException(String message) {
        super(message);
    }

    public FoldersNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoldersNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public FoldersNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
