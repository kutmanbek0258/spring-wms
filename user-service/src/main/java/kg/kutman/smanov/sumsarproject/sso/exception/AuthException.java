package kg.kutman.smanov.sumsarproject.sso.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;
import kg.kutman.smanov.sumsarproject.sso.type.AuthErrorCode;

@Getter
public class AuthException extends AuthenticationException {

    private final AuthErrorCode errorCode;

    public AuthException(AuthErrorCode errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public AuthException(String msg, AuthErrorCode errorCode) {
        super(msg);
        this.errorCode = errorCode;
    }

    public AuthException(AuthErrorCode errorCode) {
        super(null);
        this.errorCode = errorCode;
    }
}
