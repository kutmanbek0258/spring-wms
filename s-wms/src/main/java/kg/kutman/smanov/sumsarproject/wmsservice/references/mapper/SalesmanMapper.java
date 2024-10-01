package kg.kutman.smanov.sumsarproject.wmsservice.references.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Salesman;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.SalesmanDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesmanMapper extends EntityMapper<SalesmanDto, Salesman> {
}