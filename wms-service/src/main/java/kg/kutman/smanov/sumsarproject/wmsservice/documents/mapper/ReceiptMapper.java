package kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.ReceiptDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.EntityMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.Receipt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceiptMapper extends EntityMapper<ReceiptDto, Receipt> {
}