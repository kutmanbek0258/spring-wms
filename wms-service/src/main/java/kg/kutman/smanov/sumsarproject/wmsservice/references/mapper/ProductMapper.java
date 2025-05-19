package kg.kutman.smanov.sumsarproject.wmsservice.references.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.ProductDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDto, Product> {
}