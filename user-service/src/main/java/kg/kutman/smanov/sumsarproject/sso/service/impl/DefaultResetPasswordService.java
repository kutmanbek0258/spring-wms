package kg.kutman.smanov.sumsarproject.sso.service.impl;

import com.google.common.collect.ImmutableMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.kutman.smanov.sumsarproject.sso.components.ConfirmationStore;
import kg.kutman.smanov.sumsarproject.sso.components.OTPStore;
import kg.kutman.smanov.sumsarproject.sso.exception.InformationException;
import kg.kutman.smanov.sumsarproject.sso.exception.ResetPasswordException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import kg.kutman.smanov.sumsarproject.sso.config.security.properties.AuthorizationServerProperties;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEntity;
import kg.kutman.smanov.sumsarproject.sso.service.MailSenderService;
import kg.kutman.smanov.sumsarproject.sso.service.MessageService;
import kg.kutman.smanov.sumsarproject.sso.service.ResetPasswordService;
import kg.kutman.smanov.sumsarproject.sso.service.UserService;
import kg.kutman.smanov.sumsarproject.sso.utils.CryptoUtils;

@Service
@RequiredArgsConstructor
public class DefaultResetPasswordService implements ResetPasswordService {

    private static final String SESSION_ID_HEADER = "reset-password-session";

    private final MailSenderService mailSenderService;
    private final OTPStore otpStore;
    private final ConfirmationStore resetPasswordStore;
    private final AuthorizationServerProperties authorizationServerProperties;
    private final UserService userService;
    private final MessageService messageService;

    @Override
    public void initial(String email, HttpServletResponse response) {
        email = email.trim().toLowerCase();
        if (!userService.existByEmail(email)) {
            throw InformationException.builder("$email.not.found").build();
        }

        OTPStore.GenerationResult generationResult = otpStore.generate(response);
        try {
            resetPasswordStore.save(
                new ConfirmationStore.StoreItem(email, generationResult.otp(), null),
                generationResult.sessionId()
            );
        } catch (Exception e) {
            throw InformationException.builder("$happened.unexpected.error").build();
        }
        UserEntity user = userService.findByEmail(email);
        mailSenderService.sendNewMail(
            email,
            messageService.getMessage("email.subject.init.reset.password"),
            ImmutableMap.<String, Object>builder()
                .put("firstName", user.getFirstName())
                .put("otp", generationResult.otp())
                .build()
                .toString()
        );
    }

    @Override
    public void confirmEmail(String otp, HttpServletRequest request) {
        otp = otp.trim();
        if (!otpStore.validate(otp, request)) {
            throw new ResetPasswordException("$opt.incorrect");
        }

        // по идентификатору по OTPStore получаем данные из resetPasswordStore. Там находиться email пользователя.
        // Он был сохранён на первом шаге.
        String sessionId = otpStore.getSessionId(request);
        ConfirmationStore.StoreItem storeItem;
        try {
            storeItem = resetPasswordStore.take(sessionId);
        } catch (Exception e) {
            throw InformationException.builder("$happened.unexpected.error").build();
        }

        // Генерируем специальный идентификатор сессии, который укажем в email сообщении.
        String resetPasswordSessionId = CryptoUtils.hash(sessionId + "-" + otp);
        try {
            resetPasswordStore.save(storeItem, resetPasswordSessionId);
        } catch (Exception e) {
            throw InformationException.builder("$happened.unexpected.error").build();
        }

        // Находим пользователя по email в БД. Он нам нужен, для того чтобы добавить в сообщение имя пользователя.
        UserEntity user = userService.findByEmail(storeItem.email());

        // отправляем email сообщение.
        mailSenderService.sendNewMail(
            storeItem.email(),
            messageService.getMessage("email.subject.reset.password"),
            ImmutableMap.<String, Object>builder()
                .put("firstName", user.getFirstName())
                .put("resetPasswordUrl", this.getResetPasswordUrl(resetPasswordSessionId))
                .build()
                .toString()
        );
    }

    /**
     * Генерация URL на форму сброса пароля
     *
     * @param sessionId специальный идентификатор сессии
     */
    private String getResetPasswordUrl(String sessionId) {
        String httpUrl = authorizationServerProperties.getIssuerUrl()
            + authorizationServerProperties.getResetPasswordEndpoint();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(httpUrl);
        builder.queryParam("resetSessionId", sessionId);
        return builder.build().toUriString();
    }

    @Override
    public void setNewPassword(String newPassword, HttpServletRequest request) {
        // Проверяем существует ли в запросе специальный заголовок
        if (request.getHeader(SESSION_ID_HEADER) == null) {
            throw new ResetPasswordException("$reset.password.broke");
        }

        // Пытаемся получить значение специального заголовка.
        String resetPasswordSessionId = request.getHeader(SESSION_ID_HEADER);

        // Пытаемся получить данные из resetPasswordStore по значению из заголовка
        ConfirmationStore.StoreItem storeItem;
        try {
            storeItem = resetPasswordStore.take(resetPasswordSessionId);
        } catch (Exception e) {
            throw InformationException.builder("$happened.unexpected.error").build();
        }

        // Если данных нет, то выбрасываем ошибку
        if (storeItem == null) {
            throw new ResetPasswordException("$reset.password.broke");
        }

        // Если данные есть, то меняем пароль у пользователя. Email берём тот который получили из resetPasswordStore
        userService.changePassword(storeItem.email(), newPassword);
    }
}
