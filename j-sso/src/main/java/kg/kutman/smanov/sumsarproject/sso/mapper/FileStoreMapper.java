package kg.kutman.smanov.sumsarproject.sso.mapper;

import kg.kutman.smanov.sumsarproject.sso.dao.entity.FileStoreEntity;
import kg.kutman.smanov.sumsarproject.sso.dto.FileStoreDto;

public class FileStoreMapper {

    public static FileStoreDto map(FileStoreEntity entity) {
        return FileStoreDto.builder()
            .bucket(entity.getBucket())
            .contentType(entity.getContentType())
            .id(entity.getId())
            .name(entity.getFilename())
            .size(entity.getFileSize())
            .build();
    }
}
