package kg.kutman.smanov.sumsarproject.wmsservice.references.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import lombok.Data;

@Data
@Entity
@Table(schema = "wms", name = "depot")
public class Depot extends AuditableCustom<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 1024)
    private String description;

    @NotBlank
    @Size(max = 1024)
    private String address;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Person manager;
}
