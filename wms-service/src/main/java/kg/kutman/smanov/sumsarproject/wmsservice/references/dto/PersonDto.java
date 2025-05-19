package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.references.annotation.CheckMobile;
import kg.kutman.smanov.sumsarproject.wmsservice.references.annotation.CheckEmail;

public class PersonDto extends AbstractDto<Long> {
    private Long id;
    private String fullName;
    @CheckMobile
    private String phone;
    @CheckEmail
    private String email;
    private String address;

    public PersonDto() {
    }

    @Override
    public String toString() {
        return "PersonDto{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }
}