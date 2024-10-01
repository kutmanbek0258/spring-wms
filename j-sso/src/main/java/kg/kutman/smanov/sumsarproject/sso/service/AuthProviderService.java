package kg.kutman.smanov.sumsarproject.sso.service;

import org.springframework.security.oauth2.core.user.OAuth2User;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEntity;
import kg.kutman.smanov.sumsarproject.sso.dto.security.AuthorizedUser;
import kg.kutman.smanov.sumsarproject.sso.type.AuthProvider;

public interface AuthProviderService {

    /**
     * Создание или обновление пользователя используя сервис-провайдер
     */
    UserEntity save(OAuth2User userDto, AuthProvider provider);

    /**
     * Создание или обновление пользователя с последующим маппингом в сущность AuthorizedUser
     */
    AuthorizedUser saveAndMap(OAuth2User userDto, AuthProvider provider);
}
