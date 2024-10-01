package kg.kutman.smanov.sumsarproject.sso.dao.type;

import lombok.Getter;

@Getter
public enum StoreType {
    AVATAR("avatars");

    /**
     * Имя раздела (папки)
     **/
    private final String bucket;

    StoreType(String bucket) {
        this.bucket = bucket;
    }
}
