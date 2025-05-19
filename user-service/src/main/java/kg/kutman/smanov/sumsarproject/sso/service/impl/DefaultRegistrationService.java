package kg.kutman.smanov.sumsarproject.sso.service.impl;

import com.google.common.collect.ImmutableMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.kutman.smanov.sumsarproject.sso.components.OTPStore;
import kg.kutman.smanov.sumsarproject.sso.components.RegistrationStore;
import kg.kutman.smanov.sumsarproject.sso.exception.InformationException;
import kg.kutman.smanov.sumsarproject.sso.exception.RegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kg.kutman.smanov.sumsarproject.sso.dto.RegistrationDto;
import kg.kutman.smanov.sumsarproject.sso.service.MailSenderService;
import kg.kutman.smanov.sumsarproject.sso.service.MessageService;
import kg.kutman.smanov.sumsarproject.sso.service.RegistrationService;
import kg.kutman.smanov.sumsarproject.sso.service.UserService;

@Service
@RequiredArgsConstructor
public class DefaultRegistrationService implements RegistrationService {

    private final UserService userService;
    private final MailSenderService mailSenderService;
    private final OTPStore otpStore;
    private final RegistrationStore registrationStore;
    private final MessageService messageService;

    @Override
    public void register(RegistrationDto registrationDto, HttpServletResponse response) {
        // проверяем что пользователь с таким email ещё не существует
        if (userService.existByEmail(registrationDto.getEmail())) {
            throw InformationException.builder("$account.already.exist").build();
        }

        // Создаём OTP
        OTPStore.GenerationResult generationResult = otpStore.generate(response);

        // Сохраняем данные во временное хранилище
        try {
            registrationStore.save(registrationDto, generationResult.sessionId());
        } catch (Exception e) {
            throw InformationException.builder("$happened.unexpected.error").build();
        }

        // отправляем OTP по email
        mailSenderService.sendNewMail(
            registrationDto.getEmail(),
            messageService.getMessage("email.subject.confirm.registration"),
            ImmutableMap.<String, Object>builder()
                .put("firstName", registrationDto.getFirstName())
                .put("otp", generationResult.otp())
                .build()
                .toString()
        );
    }

    @Override
    @Transactional
    public void checkOtp(String otp, HttpServletRequest request) {
        if (!otpStore.validate(otp, request)) {
            throw new RegistrationException("$opt.incorrect");
        }

        String sessionId = otpStore.getSessionId(request);
        RegistrationDto registrationDto;
        try {
            registrationDto = registrationStore.take(sessionId);
        } catch (Exception e) {
            throw InformationException.builder("$happened.unexpected.error").build();
        }
        userService.saveAndActivateUser(registrationDto);
    }
}
