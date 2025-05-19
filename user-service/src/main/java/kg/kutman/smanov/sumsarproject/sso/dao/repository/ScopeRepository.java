package kg.kutman.smanov.sumsarproject.sso.dao.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.ScopeEntity;

public interface ScopeRepository extends JpaRepository<ScopeEntity, UUID> {

}
