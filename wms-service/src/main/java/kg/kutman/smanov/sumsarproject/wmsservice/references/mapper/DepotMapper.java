package kg.kutman.smanov.sumsarproject.wmsservice.references.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.DepotDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepotMapper extends EntityMapper<DepotDto, Depot> {
}