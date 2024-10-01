package kg.kutman.smanov.sumsarproject.sso.exception;

import kg.kutman.smanov.sumsarproject.sso.type.ErrorLevel;

public class ResetPasswordException extends InformationException {

    public ResetPasswordException(String description) {
        super(description, null, ErrorLevel.ERROR);
    }

    public ResetPasswordException(String message, Throwable cause) {
        super(message, cause, ErrorLevel.ERROR);
    }
}
