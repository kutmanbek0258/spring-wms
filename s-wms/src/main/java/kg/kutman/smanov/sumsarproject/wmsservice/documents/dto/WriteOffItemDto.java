package kg.kutman.smanov.sumsarproject.wmsservice.documents.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOff;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Product;

public class WriteOffItemDto extends AbstractDto<Long> {
    private Long id;
    private WriteOff writeOff;
    private Product product;
    private Double quantity;

    public WriteOffItemDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setWriteOff(WriteOff writeOff) {
        this.writeOff = writeOff;
    }

    public WriteOff getWriteOff() {
        return this.writeOff;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getQuantity() {
        return this.quantity;
    }
}