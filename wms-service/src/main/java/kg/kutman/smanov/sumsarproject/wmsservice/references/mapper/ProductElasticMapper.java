package kg.kutman.smanov.sumsarproject.wmsservice.references.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.ProductDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.ProductElastic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductElasticMapper extends EntityMapper<ProductDto, ProductElastic> {
}