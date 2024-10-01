package kg.kutman.smanov.sumsarproject.sso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kg.kutman.smanov.sumsarproject.sso.dto.ReferenceDto;
import kg.kutman.smanov.sumsarproject.sso.service.ReferenceService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reference")
@Tag(name = "Контроллер получения справочников")
public class ReferenceController {

    private final ReferenceService referenceService;

    @GetMapping("/auth-methods")
    @Operation(description = "Получения методов авторизации OAuth2 клиентов")
    public List<ReferenceDto<String>> getAuthMethods() {
        return referenceService.getAuthMethods();
    }

    @GetMapping("/grant-types")
    @Operation(description = "Получение доступных grant type OAuth2 клиентов")
    public List<ReferenceDto<String>> getGrantTypes() {
        return referenceService.getGrantTypes();
    }

    @GetMapping("/scopes")
    @Operation(description = "Получение доступных scopes OAuth2 клиентов")
    public List<ReferenceDto<String>> getScopes() {
        return referenceService.getScopes();
    }
}
