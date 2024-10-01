package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.references.annotation.CheckMobile;
import kg.kutman.smanov.sumsarproject.wmsservice.references.annotation.CheckEmail;

public class CompanyDto extends AbstractDto<Long> {
    private Long id;
    private String name;
    private String description;
    private String address;
    @CheckMobile
    private String phone;
    @CheckEmail
    private String email;

    public CompanyDto() {
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }
}