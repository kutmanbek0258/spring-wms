package kg.kutman.smanov.sumsarproject.wmsservice.documents.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "wms", name = "write_off")
public class WriteOff extends AuditableCustom<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;

    @Enumerated(EnumType.ORDINAL)
    private WriteOffReason reason;

    @OneToMany
    @JoinColumn(name = "write_off_item_id")
    private List<WriteOffItem> items;
}
