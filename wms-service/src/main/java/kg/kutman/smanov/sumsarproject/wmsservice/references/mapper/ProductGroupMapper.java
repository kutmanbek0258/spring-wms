package kg.kutman.smanov.sumsarproject.wmsservice.references.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.ProductGroupDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.ProductGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductGroupMapper extends EntityMapper<ProductGroupDto, ProductGroup> {
}