package kg.kutman.smanov.sumsarproject.sso.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

import kg.kutman.smanov.sumsarproject.sso.dto.converters.AuthMethodJsonDeserializer;
import kg.kutman.smanov.sumsarproject.sso.dto.converters.AuthMethodJsonSerializer;
import kg.kutman.smanov.sumsarproject.sso.dto.converters.GrantTypeJsonDeserializer;
import kg.kutman.smanov.sumsarproject.sso.dto.converters.GrantTypeJsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2ClientDto {

    @NotNull
    private String clientId;
    private String clientSecret;
    private LocalDate clientSecretExpiresAt;
    private String clientName;

    @JsonSerialize(contentUsing = AuthMethodJsonSerializer.class)
    @JsonDeserialize(contentUsing = AuthMethodJsonDeserializer.class)
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @JsonSerialize(contentUsing = GrantTypeJsonSerializer.class)
    @JsonDeserialize(contentUsing = GrantTypeJsonDeserializer.class)
    private Set<AuthorizationGrantType> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private Set<String> deleteNotifyUris;
}
