package kg.kutman.smanov.sumsarproject.sso.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import kg.kutman.smanov.sumsarproject.sso.dto.PageableResponseDto;
import kg.kutman.smanov.sumsarproject.sso.dto.UserEventDto;
import kg.kutman.smanov.sumsarproject.sso.service.UserEventService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user-event")
@Tag(name = "Контроллер управления событиями безопасности пользователя")
public class UserEventController {

    private final UserEventService userEventService;

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('GET_OWN_EVENTS')")
    @Operation(description = "Получение событий безопасности пользователя")
    public PageableResponseDto<UserEventDto> searchUserEvents(
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize
    ) {
        return userEventService.searchEvents(page, pageSize);
    }
}
