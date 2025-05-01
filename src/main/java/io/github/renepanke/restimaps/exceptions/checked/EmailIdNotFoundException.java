package io.github.renepanke.restimaps.exceptions.checked;

public class EmailIdNotFoundException extends RestImapException{
    public EmailIdNotFoundException() {
    }

    public EmailIdNotFoundException(String message) {
        super(message);
    }

    public EmailIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailIdNotFoundException(Throwable cause) {
        super(cause);
    }

    public EmailIdNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
