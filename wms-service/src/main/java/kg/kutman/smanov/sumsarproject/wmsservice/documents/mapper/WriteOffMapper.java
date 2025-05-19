package kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.WriteOffDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOff;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WriteOffMapper extends EntityMapper<WriteOffDto, WriteOff> {
}