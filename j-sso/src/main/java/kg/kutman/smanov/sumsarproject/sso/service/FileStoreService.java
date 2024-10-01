package kg.kutman.smanov.sumsarproject.sso.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import kg.kutman.smanov.sumsarproject.sso.dao.type.StoreType;
import kg.kutman.smanov.sumsarproject.sso.dto.FileStoreDto;

public interface FileStoreService {

    /**
     * Создать новый файл в хранилище или заменить существующий.
     */
    FileStoreDto saveOrReplace(MultipartFile file, StoreType storeType, UUID existedFileId);

    /**
     * Создать новый файл в хранилище или заменить существующий, используя прямую URL ссылку на файл внешнего ресурса.
     */
    FileStoreDto saveOrReplace(String externalUrl, StoreType storeType, UUID existedFileId);

    /**
     * Получить информацию о файле по идентификатору.
     */
    FileStoreDto getById(UUID fileId);

    /**
     * Удалить информацию и сам файл по его идентификатору
     */
    void delete(UUID fileId);

    /**
     * Удалить информацию и файлы по списку идентификаторов
     */
    void deleteList(List<UUID> fileIds);

    /**
     * Получить файл из хранилища в виде массива байт.
     */
    byte[] download(UUID fileId) throws IOException;

    /**
     * Проверить существует ли файл в хранилище.
     */
    Boolean isExists(UUID fileId, StoreType storeType);
}
