package kg.kutman.smanov.sumsarproject.wmsservice.references.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kg.kutman.smanov.sumsarproject.wmsservice.config.audit.AuditableCustom;
import lombok.Data;

@Data
@Entity
@Table(schema = "wms", name = "person")
public class Person extends AuditableCustom<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1024)
    private String fullName;

    @NotBlank
    @Size(max = 1024)
    private String phone;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 1024)
    private String address;
}
