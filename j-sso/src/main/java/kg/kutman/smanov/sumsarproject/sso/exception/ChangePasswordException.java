package kg.kutman.smanov.sumsarproject.sso.exception;


import kg.kutman.smanov.sumsarproject.sso.type.ErrorLevel;

public class ChangePasswordException extends InformationException {

    public ChangePasswordException(String description) {
        super(description, null, ErrorLevel.ERROR);
    }

    public ChangePasswordException(String message, Throwable cause) {
        super(message, cause, ErrorLevel.ERROR);
    }
}
