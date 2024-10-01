package kg.kutman.smanov.sumsarproject.sso.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileStoreDto {

    private UUID id;
    private String name;
    private String bucket;
    private String contentType;
    private Long size;
}
