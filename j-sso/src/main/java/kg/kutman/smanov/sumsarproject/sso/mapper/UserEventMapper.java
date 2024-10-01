package kg.kutman.smanov.sumsarproject.sso.mapper;

import kg.kutman.smanov.sumsarproject.sso.service.MessageService;
import lombok.experimental.UtilityClass;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEventEntity;
import kg.kutman.smanov.sumsarproject.sso.dto.UserEventDto;

@UtilityClass
public class UserEventMapper {

    public UserEventDto map(UserEventEntity entity, MessageService messageService) {
        return UserEventDto.builder()
            .id(entity.getId())
            .eventType(entity.getEventType())
            .eventTypeName(messageService.getMessage(entity.getEventType()))
            .ipAddress(entity.getIpAddress())
            .clientId(entity.getClientId())
            .browser(entity.getBrowser())
            .device(entity.getDevice())
            .os(entity.getOs())
            .creationDate(entity.getCreationDate())
            .build();
    }
}
