package kg.kutman.smanov.sumsarproject.sso.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

import kg.kutman.smanov.sumsarproject.sso.dao.entity.common.VersionedBusinessEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(schema = "sso", name = "user_clients")
public class UserClient extends VersionedBusinessEntity<UUID> {

    @Id
    @Column(name = "user_client_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "deleted")
    private Boolean deleted;

}
