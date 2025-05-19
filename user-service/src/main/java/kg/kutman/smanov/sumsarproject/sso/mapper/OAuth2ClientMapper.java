package kg.kutman.smanov.sumsarproject.sso.mapper;

import lombok.experimental.UtilityClass;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.SystemOauth2Client;
import kg.kutman.smanov.sumsarproject.sso.dto.OAuth2ClientDto;

@UtilityClass
public class OAuth2ClientMapper {

    public OAuth2ClientDto map(SystemOauth2Client entity) {
        return OAuth2ClientDto.builder()
            .clientId(entity.getClientId())
            .clientSecret(entity.getClientSecret())
            .clientName(entity.getClientName())
            .clientSecretExpiresAt(
                entity.getClientSecretExpiresAt() != null ? entity.getClientSecretExpiresAt().toLocalDate() : null
            )
            .clientAuthenticationMethods(entity.getClientAuthenticationMethods())
            .authorizationGrantTypes(entity.getAuthorizationGrantTypes())
            .redirectUris(entity.getRedirectUris())
            .scopes(entity.getScopes())
            .deleteNotifyUris(entity.getDeleteNotifyUris())
            .build();
    }
}
