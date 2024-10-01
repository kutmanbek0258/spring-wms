package kg.kutman.smanov.sumsarproject.sso.dao.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserClient;

public interface UserClientRepository extends JpaRepository<UserClient, String> {

    UserClient findByUserIdAndClientId(UUID userId, String clientId);

    List<UserClient> findByUserId(UUID userId);

    List<UserClient> findAllByDeletedIsTrue();

    void deleteAllByDeletedIsTrue();

}
