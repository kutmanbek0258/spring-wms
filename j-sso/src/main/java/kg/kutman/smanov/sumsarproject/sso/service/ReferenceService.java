package kg.kutman.smanov.sumsarproject.sso.service;

import java.util.Collection;
import java.util.List;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import kg.kutman.smanov.sumsarproject.sso.dto.ReferenceDto;

public interface ReferenceService {

    List<ReferenceDto<String>> getAuthMethods();

    List<ReferenceDto<String>> getGrantTypes();

    List<ReferenceDto<String>> getScopes();

    String getGrantTypeName(AuthorizationGrantType grantType);

    String getScopeName(String scope);

    List<String> getScopeNames(Collection<String> scopeCodes);
}
