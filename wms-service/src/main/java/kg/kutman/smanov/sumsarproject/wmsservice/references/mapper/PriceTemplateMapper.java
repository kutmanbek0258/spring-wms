package kg.kutman.smanov.sumsarproject.wmsservice.references.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.PriceTemplateDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.PriceTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceTemplateMapper extends EntityMapper<PriceTemplateDto, PriceTemplate> {
}