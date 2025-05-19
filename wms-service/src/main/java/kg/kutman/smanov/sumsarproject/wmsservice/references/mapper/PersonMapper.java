package kg.kutman.smanov.sumsarproject.wmsservice.references.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.PersonDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDto, Person> {
}