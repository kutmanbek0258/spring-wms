package kg.kutman.smanov.sumsarproject.sso.service;

import java.util.UUID;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEntity;
import kg.kutman.smanov.sumsarproject.sso.dto.FileStoreDto;
import kg.kutman.smanov.sumsarproject.sso.dto.RegistrationDto;

public interface UserService {

    /**
     * Создание пользователя на основе регистрационных данных. Пользователь будет не активирован.
     *
     * @param userDto данные указанные при регистрации
     */
    UserEntity saveUser(RegistrationDto userDto);

    /**
     * Активация пользователя
     *
     * @param userId   уникальный идентификатор пользователя
     * @param password пароль пользователя
     */
    UserEntity firstActivation(UUID userId, String password);

    /**
     * Создать пользователя и сразу активировать
     */
    UserEntity saveAndActivateUser(RegistrationDto userDto);

    /**
     * Проверить существует ли пользователь с указанным email
     */
    boolean existByEmail(String email);

    /**
     * Найти entity пользователя по email
     */
    UserEntity findByEmail(String email);

    /**
     * Сменить пароль у пользователя с указанным email
     */
    void changePassword(String email, String password);

    /**
     * Получение аватара пользователя
     */
    UserAvatar getUserAvatar(UUID userId);

    record UserAvatar(FileStoreDto storeDto, byte[] avatar) { }
}
