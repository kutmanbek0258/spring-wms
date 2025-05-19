package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.PriceTemplate;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.ProductGroup;

public class ProductDto extends AbstractDto<Long> {
    private Long id;
    private String name;
    private String description;
    private String barcode;
    private Double quantity;
    private Double price;
    private Double cost;
    private PriceTemplate priceTemplate;
    private ProductGroup productGroup;

    public ProductDto() {
    }

    public ProductDto(Long id, String name, String description, String barcode, PriceTemplate priceTemplate, ProductGroup productGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.priceTemplate = priceTemplate;
        this.productGroup = productGroup;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", barcode='" + barcode + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", cost=" + cost +
                ", priceTemplate=" + priceTemplate +
                ", productGroup=" + productGroup +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcode() {
        return this.barcode;
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

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setPriceTemplate(PriceTemplate priceTemplate) {
        this.priceTemplate = priceTemplate;
    }

    public PriceTemplate getPriceTemplate() {
        return this.priceTemplate;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }

    public ProductGroup getProductGroup() {
        return this.productGroup;
    }
}