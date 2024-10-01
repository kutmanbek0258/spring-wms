package kg.kutman.smanov.sumsarproject.sso.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.kutman.smanov.sumsarproject.sso.dto.RegistrationDto;

public interface RegistrationService {

    /**
     * Сохранение регистрационных данных, генерация OTP, отправка по email OTP.
     */
    void register(RegistrationDto registrationDto, HttpServletResponse response);

    /**
     * Валидация OTP и создание пользователя
     */
    void checkOtp(String otp, HttpServletRequest request);
}
