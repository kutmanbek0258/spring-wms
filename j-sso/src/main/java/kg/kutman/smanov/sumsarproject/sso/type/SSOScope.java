package kg.kutman.smanov.sumsarproject.sso.type;

import lombok.Getter;

@Getter
public enum SSOScope {
    USER_IDENTIFICATION("SSO.USER_IDENTIFICATION"),
    USER_PROFILE_INFO("SSO.USER_PROFILE_INFO"),
    USER_AVATAR("SSO.USER_AVATAR"),
    USER_AUTHORITIES("SSO.USER_AUTHORITIES");

    private final String databaseCode;

    SSOScope(String databaseCode) {
        this.databaseCode = databaseCode;
    }

    public static SSOScope findByName(String name) {
        try {
            return valueOf(name);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
