package kg.kutman.smanov.sumsarproject.sso.service.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import kg.kutman.smanov.sumsarproject.sso.dto.security.AuthorizedUser;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final SecurityContextRepository securityContextRepository;

    /**
     * Обновление информации о пользователе в Security Context на основе подготовленной DTO.
     */
    public void reloadSecurityContext(
        AuthorizedUser authorizedUser,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        SecurityContext securityContext = this.reloadAuthenticationWithNewPrincipal(authorizedUser);
        securityContextRepository.saveContext(securityContext, request, response);
    }

    /**
     * Обновление объекта Authentication в Security Context используя новую информацию о пользователе из указанной DTO.
     */
    private SecurityContext reloadAuthenticationWithNewPrincipal(AuthorizedUser principal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UnsupportedOperationException("Authentication object is null");
        }

        if (authentication instanceof UsernamePasswordAuthenticationToken auth) {
            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                principal,
                auth.getCredentials(),
                auth.getAuthorities()
            );
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(newAuth);
            return securityContext;
        }
        if (authentication instanceof OAuth2AuthenticationToken auth) {
            Authentication newAuth = new OAuth2AuthenticationToken(
                principal,
                auth.getAuthorities(),
                auth.getAuthorizedClientRegistrationId()
            );
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(newAuth);
            return securityContext;
        }
        throw new UnsupportedOperationException(
            "Authentication type " + authentication.getClass() + " is not supported");
    }
}
