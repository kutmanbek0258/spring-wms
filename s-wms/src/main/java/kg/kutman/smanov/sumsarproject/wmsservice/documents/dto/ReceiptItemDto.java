package kg.kutman.smanov.sumsarproject.wmsservice.documents.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Product;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.Receipt;

public class ReceiptItemDto extends AbstractDto<Long> {
    private Long id;
    private Receipt receipt;
    private Product product;
    private Double quantity;
    private Double price;

    public ReceiptItemDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public Receipt getReceipt() {
        return this.receipt;
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return this.price;
    }
}