package kg.kutman.smanov.sumsarproject.wmsservice.documents.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.ReceiptDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper.ReceiptMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.Receipt;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.repository.ReceiptRepository;
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
public class ReceiptService {
    private final ReceiptRepository repository;
    private final ReceiptMapper receiptMapper;

    public ReceiptService(ReceiptRepository repository, ReceiptMapper receiptMapper) {
        this.repository = repository;
        this.receiptMapper = receiptMapper;
    }

    public ReceiptDto save(ReceiptDto receiptDto) {
        Receipt entity = receiptMapper.toEntity(receiptDto);
        return receiptMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ReceiptDto findById(Long id) {
        return receiptMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<ReceiptDto> findByCondition(ReceiptDto receiptDto, Pageable pageable) {
        Page<Receipt> entityPage = repository.findAll(pageable);
        List<Receipt> entities = entityPage.getContent();
        return new PageImpl<>(receiptMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ReceiptDto update(ReceiptDto receiptDto, Long id) {
        ReceiptDto data = findById(id);
        Receipt entity = receiptMapper.toEntity(receiptDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}