package kg.kutman.smanov.sumsarproject.sso.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import kg.kutman.smanov.sumsarproject.sso.dao.type.UserEventType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDto {

    private UUID id;
    private UserEventType eventType;
    private String eventTypeName;
    private String ipAddress;
    private String clientId;
    private String browser;
    private String device;
    private String os;
    private LocalDateTime creationDate;
}
