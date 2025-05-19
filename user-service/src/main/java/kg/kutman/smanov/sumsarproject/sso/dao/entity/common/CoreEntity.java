package kg.kutman.smanov.sumsarproject.sso.dao.entity.common;

import java.io.Serializable;

public interface CoreEntity<Id extends Serializable> extends Serializable {

    Id getId();

    void setId(Id id);
}
