package kg.kutman.smanov.sumsarproject.sso.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenInfoDto {

    private String authorizationId;
    private LocalDateTime startDate;
    private LocalDateTime lastRefreshDate;
    private String clientId;
    private String clientName;
    private List<String> scopeNames;
    private String grantTypeName;
    private String clientRedirectUri;
}
