package kg.kutman.smanov.sumsarproject.sso.dao.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.FileStoreEntity;

@Repository
public interface FileStoreRepository extends JpaRepository<FileStoreEntity, UUID> {
}
