package kg.kutman.smanov.sumsarproject.sso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kg.kutman.smanov.sumsarproject.sso.dto.RegistrationDto;
import kg.kutman.smanov.sumsarproject.sso.service.RegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
@Tag(name = "Контроллер процесса регистрации")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/init")
    @Operation(description = "Получение информации о новом пользователе и отправка OTP кода подтверждения")
    public void registerNewUser(@RequestBody RegistrationDto dto, HttpServletResponse response) {
        registrationService.register(dto, response);
    }

    @PostMapping("/confirm")
    @Operation(description = "Подтверждение кода OTP полученного на первом этапе")
    public void checkOtp(@RequestParam("otp") String otp, HttpServletRequest request) {
        registrationService.checkOtp(otp, request);
    }
}
