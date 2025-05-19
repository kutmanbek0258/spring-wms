package kg.kutman.smanov.sumsarproject.wmsservice.references.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductCsvDto extends AbstractDto<Long> {
    @CsvBindByName(column = "name",required = true)
    private String name;
    @CsvBindByName(column = "description")
    private String description;
    @CsvBindByName(column = "barcode")
    private String barcode;
    @CsvBindByName(column = "priceTemplateID")
    private Long priceTemplateID;
    @CsvBindByName(column = "productGroupID")
    private Long ProductGroupID;
}