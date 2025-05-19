package kg.kutman.smanov.sumsarproject.wmsservice.documents.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Supplier;

public class ReceiptDto extends AbstractDto<Long> {
    private Long id;
    private Depot depot;
    private Supplier supplier;

    public ReceiptDto() {
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

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }
}