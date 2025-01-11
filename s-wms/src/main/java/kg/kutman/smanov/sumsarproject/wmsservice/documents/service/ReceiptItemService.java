package kg.kutman.smanov.sumsarproject.wmsservice.documents.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.ReceiptItemDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper.ReceiptItemMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.Receipt;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.ReceiptItem;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.repository.ReceiptItemRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class ReceiptItemService {
    private final ReceiptItemRepository repository;
    private final ReceiptItemMapper receiptItemMapper;

    public ReceiptItemService(ReceiptItemRepository repository, ReceiptItemMapper receiptItemMapper) {
        this.repository = repository;
        this.receiptItemMapper = receiptItemMapper;
    }

    public ReceiptItemDto save(ReceiptItemDto receiptItemDto) {
        ReceiptItem entity = receiptItemMapper.toEntity(receiptItemDto);
        return receiptItemMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ReceiptItemDto findById(Long id) {
        return receiptItemMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<ReceiptItemDto> findByCondition(Long id, Pageable pageable) {
        Receipt receipt = new Receipt(id);
        Page<ReceiptItem> entityPage = repository.findByReceipt(receipt, pageable);
        List<ReceiptItem> entities = entityPage.getContent();
        return new PageImpl<>(receiptItemMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ReceiptItemDto update(ReceiptItemDto receiptItemDto, Long id) {
        ReceiptItemDto data = findById(id);
        ReceiptItem entity = receiptItemMapper.toEntity(receiptItemDto);
        BeanUtil.copyProperties(entity, data);
        return save(data);
    }
}