package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

public class ProductGroupDto extends AbstractDto<Long> {
    private Long id;
    private String name;
    private String description;

    public ProductGroupDto() {
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
}