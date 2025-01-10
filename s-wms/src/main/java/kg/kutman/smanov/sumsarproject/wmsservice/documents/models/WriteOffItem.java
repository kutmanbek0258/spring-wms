package kg.kutman.smanov.sumsarproject.wmsservice.documents.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "wms", name = "write_off_item")
public class WriteOffItem extends AuditableCustom<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "write_off_id")
    private WriteOff writeOff;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Double quantity;

}
