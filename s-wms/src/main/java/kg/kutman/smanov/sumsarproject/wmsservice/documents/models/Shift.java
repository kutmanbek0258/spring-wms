package kg.kutman.smanov.sumsarproject.wmsservice.documents.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import java.util.Date;

import static jakarta.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = "wms", name = "shift")
public class Shift extends AuditableCustom<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date startDate;

    @Temporal(TIMESTAMP)
    private Date endDate;

    @Enumerated(EnumType.ORDINAL)
    private ShiftStatus status;

    @ManyToOne
    @JoinColumn(name = "depot_id")
    private Depot depot;
}
