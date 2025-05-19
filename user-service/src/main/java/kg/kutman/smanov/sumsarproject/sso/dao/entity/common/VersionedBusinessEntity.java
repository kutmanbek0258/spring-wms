package kg.kutman.smanov.sumsarproject.sso.dao.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class VersionedBusinessEntity<Id extends Serializable> extends BusinessEntity<Id> {

    @Version
    @Column(name = "object_version_number", nullable = false)
    private Long version;

}
