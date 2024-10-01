package kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.ReceiptItemDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.ReceiptItem;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReceiptItemMapper extends EntityMapper<ReceiptItemDto, ReceiptItem> {
}