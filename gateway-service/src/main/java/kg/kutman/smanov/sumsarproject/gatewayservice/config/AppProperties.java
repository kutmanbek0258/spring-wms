package kg.kutman.smanov.sumsarproject.gatewayservice.config;

import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProperties {

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties(prefix = "spring.mvc.cors")
    public static class CorsProperties {

        private List<CorsConfig> configs;

        public static record CorsConfig(
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
}