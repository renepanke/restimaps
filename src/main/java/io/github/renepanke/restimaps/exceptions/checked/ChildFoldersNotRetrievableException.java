package io.github.renepanke.restimaps.exceptions.checked;

public class ChildFoldersNotRetrievableException extends RestImapException{
    public ChildFoldersNotRetrievableException() {
    }

    public ChildFoldersNotRetrievableException(String message) {
        super(message);
    }

    public ChildFoldersNotRetrievableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChildFoldersNotRetrievableException(Throwable cause) {
        super(cause);
    }

    public ChildFoldersNotRetrievableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
