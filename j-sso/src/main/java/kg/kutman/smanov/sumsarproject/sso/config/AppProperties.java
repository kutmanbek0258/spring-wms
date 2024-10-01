package kg.kutman.smanov.sumsarproject.sso.config;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

@Configuration
public class AppProperties {

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "file-store")
    public static class FileStoreConfig {

        private String basePath;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "rest-clients")
    public static class RestClientsConfig {

        private int serviceClientConnectionTimeout;
        private int serviceClientRequestTimeout;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "spring.mvc.cors")
    public static class CorsProperties {

        private List<CorsConfig> configs;

        public record CorsConfig(
            String pattern,
            String allowedOrigins,
            String allowedOriginPatterns,
            String allowedHeaders,
            String exposedHeaders,
            String allowedMethods,
            Boolean allowCredentials,
            Long maxAge
        ) {

        }

        @PostConstruct
        private void validatePostConstruct() {
            if (configs == null) {
                configs = Collections.emptyList();
            }
        }
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "springdoc")
    public static class SwaggerProperties {

        private AuthTypesConfig authTypes;
        private AuthOauthConfig authOauth;

        public record AuthTypesConfig(
            Boolean authHeaderEnabled,
            Boolean clientCredentialsEnabled,
            Boolean authorizationCodeEnabled
        ) {

        }

        public record AuthOauthConfig(
            String tokenUrl,
            String authorizationUrl,
            String refreshUrl
        ) {

        }

        @PostConstruct
        private void validatePostConstruct() {
            if (authTypes == null) {
                authTypes = new AuthTypesConfig(false, false, false);
            }
            if (authTypes.authorizationCodeEnabled()) {
                Assert.notNull(authOauth, "Properties 'auth-oauth' must not be empty");
                Assert.notNull(authOauth.tokenUrl(), "Properties 'auth-oauth.token-url' must not be empty");
                Assert.notNull(
                    authOauth.authorizationUrl(),
                    "Properties 'auth-oauth.authorization-url' must not be empty"
                );
            }
            if (authTypes.clientCredentialsEnabled()) {
                Assert.notNull(authOauth, "Properties 'auth-oauth' must not be empty");
                Assert.notNull(authOauth.tokenUrl(), "Properties 'auth-oauth.token-url' must not be empty");
            }
        }
    }
}
