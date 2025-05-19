package kg.kutman.smanov.sumsarproject.wmsservice.references.models;

import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(schema = "wms", name = "company")
public class Company extends AuditableCustom<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1024)
    private String name;

    @NotBlank
    @Size(max = 10000)
    private String description;

    @NotBlank
    @Size(max = 1024)
    private String address;

    @NotBlank
    @Size(max = 100)
    private String phone;

    @NotBlank
    @Email
    private String email;
}
