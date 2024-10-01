package kg.kutman.smanov.sumsarproject.sso.dto.security;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationInfo implements Serializable {

    private LocalDateTime startDate;
    private LocalDateTime lastRefreshDate;
    private String clientId;
    private Set<String> scopes;
    private AuthorizationGrantType authorizationGrantType;
    private String authorizationId;
    private UUID userId;
    private String username;
    private String redirectUri;

}
