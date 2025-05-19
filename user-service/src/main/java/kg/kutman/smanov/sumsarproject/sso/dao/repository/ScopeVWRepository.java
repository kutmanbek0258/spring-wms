package kg.kutman.smanov.sumsarproject.sso.dao.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.ScopeVWEntity;

public interface ScopeVWRepository extends JpaRepository<ScopeVWEntity, UUID> {

    ScopeVWEntity findByUniqueCode(String uniqueCode);

    List<ScopeVWEntity> findAllByUniqueCodeIn(Collection<String> uniqueCodes);
}
