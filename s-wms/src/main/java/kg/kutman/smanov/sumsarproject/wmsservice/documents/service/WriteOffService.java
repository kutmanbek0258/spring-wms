package kg.kutman.smanov.sumsarproject.wmsservice.documents.service;

import cn.hutool.core.bean.BeanUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.WriteOffDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.mapper.WriteOffMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.models.WriteOff;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.repository.WriteOffRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
public class WriteOffService {
    private final WriteOffRepository repository;
    private final WriteOffMapper writeOffMapper;

    public WriteOffService(WriteOffRepository repository, WriteOffMapper writeOffMapper) {
        this.repository = repository;
        this.writeOffMapper = writeOffMapper;
    }

    public WriteOffDto save(WriteOffDto writeOffDto) {
        WriteOff entity = writeOffMapper.toEntity(writeOffDto);
        return writeOffMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public WriteOffDto findById(Long id) {
        return writeOffMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<WriteOffDto> findByCondition(WriteOffDto writeOffDto, Pageable pageable) {
        Page<WriteOff> entityPage = repository.findAll(pageable);
        List<WriteOff> entities = entityPage.getContent();
        return new PageImpl<>(writeOffMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public WriteOffDto update(WriteOffDto writeOffDto, Long id) {
        WriteOffDto data = findById(id);
        WriteOff entity = writeOffMapper.toEntity(writeOffDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}