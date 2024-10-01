package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

public class PriceTemplateDto extends AbstractDto<Long> {
    private Long id;
    private String name;
    private String description;
    private double formula;

    public PriceTemplateDto() {
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

    public void setFormula(double formula) {
        this.formula = formula;
    }

    public double getFormula() {
        return this.formula;
    }
}