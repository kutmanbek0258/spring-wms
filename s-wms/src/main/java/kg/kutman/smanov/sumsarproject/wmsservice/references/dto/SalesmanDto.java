package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Company;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Person;

public class SalesmanDto extends AbstractDto<Long> {
    private Long id;
    private Person person;
    private Company company;

    public SalesmanDto() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return this.company;
    }
}