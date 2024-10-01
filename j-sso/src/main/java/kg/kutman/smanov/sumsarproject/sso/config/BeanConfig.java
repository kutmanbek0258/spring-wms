package kg.kutman.smanov.sumsarproject.sso.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;

import kg.kutman.smanov.sumsarproject.sso.components.ConfirmationStore;
import kg.kutman.smanov.sumsarproject.sso.components.FileStore;
import kg.kutman.smanov.sumsarproject.sso.components.OTPStore;
import kg.kutman.smanov.sumsarproject.sso.components.RegistrationStore;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import kg.kutman.smanov.sumsarproject.sso.components.impl.LocalFileStore;
import kg.kutman.smanov.sumsarproject.sso.components.impl.RedisChangePasswordStore;
import kg.kutman.smanov.sumsarproject.sso.components.impl.RedisOTPStore;
import kg.kutman.smanov.sumsarproject.sso.components.impl.RedisRegistrationStore;
import kg.kutman.smanov.sumsarproject.sso.components.impl.RedisResetPasswordStore;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    private final AppProperties.FileStoreConfig fileStoreConfig;

    @Bean
    public OTPStore.Config otpStoreConfig(
        @Value("${otp-store.cookie-name:default-name}") String cookieName,
        @Value("${otp-store.cookie-domain:localhost}") String cookieDomain,
        @Value("${otp-store.cookie-max-age:180}") int cookieMaxAge
    ) {
        return new OTPStore.Config(cookieName, cookieDomain, cookieMaxAge);
    }

    @Bean
    public OTPStore otpStore(OTPStore.Config otpStoreConfig, StringRedisTemplate redisTemplate) {
        return new RedisOTPStore(otpStoreConfig, redisTemplate);
    }

    @Bean
    public RegistrationStore registrationStore(
        OTPStore.Config otpStoreConfig,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper
    ) {
        return new RedisRegistrationStore(otpStoreConfig.cookieMaxAge(), redisTemplate, objectMapper);
    }

    @Bean
    public ConfirmationStore resetPasswordStore(
        OTPStore.Config otpStoreConfig,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper
    ) {
        return new RedisResetPasswordStore(otpStoreConfig.cookieMaxAge(), redisTemplate, objectMapper);
    }

    @Bean
    public ConfirmationStore changePasswordStore(
        OTPStore.Config otpStoreConfig,
        StringRedisTemplate redisTemplate,
        ObjectMapper objectMapper
    ) {
        return new RedisChangePasswordStore(otpStoreConfig.cookieMaxAge(), redisTemplate, objectMapper);
    }

    @Bean
    public FileStore fileStore() {
        return new LocalFileStore(Path.of(fileStoreConfig.getBasePath()));
    }
}
