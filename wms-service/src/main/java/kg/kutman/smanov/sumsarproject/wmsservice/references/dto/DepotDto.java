package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Person;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Company;

public class DepotDto extends AbstractDto<Long> {
    private Long id;
    private String name;
    private String description;
    private String address;
    private Company company;
    private Person manager;

    public DepotDto() {
    }

    @Override
    public String toString() {
        return "DepotDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", company=" + company +
                ", manager=" + manager +
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

    public void setCompany(Company company) {
        this.company = company;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setManager(Person manager) {
        this.manager = manager;
    }

    public Person getManager() {
        return this.manager;
    }
}