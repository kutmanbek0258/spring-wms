package kg.kutman.smanov.sumsarproject.wmsservice.documents.service;

import cn.hutool.core.bean.BeanUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.WriteOffItemDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper.WriteOffItemMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOffItem;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.repository.WriteOffItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class WriteOffItemService {
    private final WriteOffItemRepository repository;
    private final WriteOffItemMapper writeOffItemMapper;

    public WriteOffItemService(WriteOffItemRepository repository, WriteOffItemMapper writeOffItemMapper) {
        this.repository = repository;
        this.writeOffItemMapper = writeOffItemMapper;
    }

    public WriteOffItemDto save(WriteOffItemDto writeOffItemDto) {
        WriteOffItem entity = writeOffItemMapper.toEntity(writeOffItemDto);
        return writeOffItemMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public WriteOffItemDto findById(Long id) {
        return writeOffItemMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<WriteOffItemDto> findByCondition(WriteOffItemDto writeOffItemDto, Pageable pageable) {
        Page<WriteOffItem> entityPage = repository.findAll(pageable);
        List<WriteOffItem> entities = entityPage.getContent();
        return new PageImpl<>(writeOffItemMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public WriteOffItemDto update(WriteOffItemDto writeOffItemDto, Long id) {
        WriteOffItemDto data = findById(id);
        WriteOffItem entity = writeOffItemMapper.toEntity(writeOffItemDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}