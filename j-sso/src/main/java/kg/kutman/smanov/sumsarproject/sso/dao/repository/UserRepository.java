package kg.kutman.smanov.sumsarproject.sso.dao.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEntity;

@Repository
public interface UserRepository extends
    JpaRepository<UserEntity, UUID>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);
}
