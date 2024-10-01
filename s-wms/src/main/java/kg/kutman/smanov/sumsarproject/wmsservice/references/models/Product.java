package kg.kutman.smanov.sumsarproject.wmsservice.references.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.Date;
import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "wms", name = "product")
public class Product extends AuditableCustom<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    private String description;

    private String barcode;

    private Double quantity;

    private Double price;

    private Double cost;

    public Product(Long id, String name, String description, String barcode, PriceTemplate priceTemplate, ProductGroup productGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.priceTemplate = priceTemplate;
        this.productGroup = productGroup;
    }

    public Product(PriceTemplate priceTemplate, ProductGroup productGroup) {
        this.priceTemplate = priceTemplate;
        this.productGroup = productGroup;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", barcode='" + barcode + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", cost=" + cost +
                ", priceTemplate=" + priceTemplate +
                ", productGroup=" + productGroup +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "price_template_id")
    private PriceTemplate priceTemplate;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroup;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}
