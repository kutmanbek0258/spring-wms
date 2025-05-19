package kg.kutman.smanov.sumsarproject.wmsservice.documents.models;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Supplier;
import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "wms", name = "receipt")
public class Receipt extends AuditableCustom<String> {

    public Receipt(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
}
