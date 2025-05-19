package kg.kutman.smanov.sumsarproject.wmsservice.references.models;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product")
@Setting(settingPath = "product.index.setting.json")
public class ProductElastic {

    @Id
    private Long id;

    @Field(type = FieldType.Text, analyzer = "product_name_analyzer")
    private String name;

    @Field(type = FieldType.Text, analyzer = "product_name_analyzer")
    private String description;

    @Field(type = FieldType.Text)
    private String barcode;

    @Field(type = FieldType.Double)
    private Double quantity;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Double)
    private Double cost;

    public ProductElastic(long id, String name, String description, String barcode, PriceTemplate priceTemplate, ProductGroup productGroup) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.priceTemplate = priceTemplate;
        this.productGroup = productGroup;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", barcode='" + barcode + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", cost=" + cost +
                ", priceTemplate=" + priceTemplate +
                ", productGroup=" + productGroup +
                '}';
    }

    @Field(type = FieldType.Nested, includeInParent = true)
    private PriceTemplate priceTemplate;

    @Field(type = FieldType.Nested, includeInParent = true)
    private ProductGroup productGroup;
}
