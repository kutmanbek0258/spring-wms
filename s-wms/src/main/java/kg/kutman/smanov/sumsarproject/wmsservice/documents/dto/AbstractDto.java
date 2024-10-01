package kg.kutman.smanov.sumsarproject.wmsservice.documents.dto;

import java.time.LocalDateTime;

public class AbstractDto<E> {

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    private String createdBy;

    private String lastModifiedBy;

    public LocalDateTime getCreateAt() {
        return createDate;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createDate = createAt;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedDate;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedDate = lastModifiedAt;
    }
}