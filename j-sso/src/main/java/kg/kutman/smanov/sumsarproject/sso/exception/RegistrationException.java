package kg.kutman.smanov.sumsarproject.sso.exception;

import kg.kutman.smanov.sumsarproject.sso.type.ErrorLevel;

public class RegistrationException extends InformationException {

    public RegistrationException(String description) {
        super(description, null, ErrorLevel.ERROR);
    }

    public RegistrationException(String message, Throwable cause) {
        super(message, cause, ErrorLevel.ERROR);
    }
}
