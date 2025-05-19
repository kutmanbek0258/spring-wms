package kg.kutman.smanov.sumsarproject.sso.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.kutman.smanov.sumsarproject.sso.dao.type.UserEventType;
import kg.kutman.smanov.sumsarproject.sso.dto.PageableResponseDto;
import kg.kutman.smanov.sumsarproject.sso.dto.UserEventDto;

public interface UserEventService {

    PageableResponseDto<UserEventDto> searchEvents(int page, int pageSize);

    void createEvent(UserEventType eventType, String clientId, HttpServletRequest request);

    /**
     * Удалить события пользователя, являющиеся устаревшими.
     */
    void deleteOldEvents();
}
