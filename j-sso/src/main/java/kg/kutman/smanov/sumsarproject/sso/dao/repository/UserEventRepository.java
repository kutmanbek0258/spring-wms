package kg.kutman.smanov.sumsarproject.sso.dao.repository;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEventEntity;

public interface UserEventRepository extends JpaRepository<UserEventEntity, UUID> {

    @Modifying
    @Query(value = "delete from sso.user_events where date(created_date) < :threshold", nativeQuery = true)
    void deleteAllLessThenCreationDate(LocalDate threshold);

    Page<UserEventEntity> findAllByCreatedBy(String username, PageRequest pageRequest);
}
