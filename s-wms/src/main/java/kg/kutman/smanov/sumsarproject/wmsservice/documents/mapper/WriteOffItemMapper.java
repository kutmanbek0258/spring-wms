package kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.WriteOffItemDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOffItem;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WriteOffItemMapper extends EntityMapper<WriteOffItemDto, WriteOffItem> {
}