package kg.kutman.smanov.sumsarproject.sso.utils;

import kg.kutman.smanov.sumsarproject.sso.exception.ServiceException;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import kg.kutman.smanov.sumsarproject.sso.dto.security.AuthorizedUser;

@UtilityClass
public class SecurityUtils {

    /**
     * Получить информацию об авторизованном пользователе из контекста безопасности.
     */
    public AuthorizedUser getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw ServiceException.builder("Authentication type not supported").build();
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AuthorizedUser authorizedUser) {
            return authorizedUser;
        }
        throw ServiceException.builder(
            "Principal class = " + principal.getClass().getSimpleName() + " is not supported").build();
    }

}
