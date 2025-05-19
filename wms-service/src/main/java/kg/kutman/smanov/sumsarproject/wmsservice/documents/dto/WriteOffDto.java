package kg.kutman.smanov.sumsarproject.wmsservice.documents.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOffItem;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOffReason;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import java.util.List;

public class WriteOffDto extends AbstractDto<Long> {
    private Long id;
    private Depot depot;
    private WriteOffReason reason;
    private List<WriteOffItem> items;

    public WriteOffDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setDepot(Depot depot) {
        this.depot = depot;
    }

    public Depot getDepot() {
        return this.depot;
    }

    public void setReason(WriteOffReason reason) {
        this.reason = reason;
    }

    public WriteOffReason getReason() {
        return this.reason;
    }

    public void setItems(java.util.List<kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOffItem> items) {
        this.items = items;
    }

    public java.util.List<kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOffItem> getItems() {
        return this.items;
    }
}