package kg.kutman.smanov.sumsarproject.sso.service;

import java.util.List;
import kg.kutman.smanov.sumsarproject.sso.dto.UserTokenInfoDto;

public interface UserTokenService {

    List<UserTokenInfoDto> getUserTokens();

    void recallToken(String authenticationId);

    void recallAllCurrentUserTokens();
}
