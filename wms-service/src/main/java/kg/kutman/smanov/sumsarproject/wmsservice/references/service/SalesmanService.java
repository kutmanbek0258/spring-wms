package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.SalesmanRepository;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.SalesmanDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.SalesmanMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Salesman;
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
public class SalesmanService {
    private final SalesmanRepository repository;
    private final SalesmanMapper salesmanMapper;

    public SalesmanService(SalesmanRepository repository, SalesmanMapper salesmanMapper) {
        this.repository = repository;
        this.salesmanMapper = salesmanMapper;
    }

    public SalesmanDto save(SalesmanDto salesmanDto) {
        Salesman entity = salesmanMapper.toEntity(salesmanDto);
        return salesmanMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public SalesmanDto findById(Long id) {
        return salesmanMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<SalesmanDto> findByCondition(SalesmanDto salesmanDto, Pageable pageable) {
        Page<Salesman> entityPage = repository.findAll(pageable);
        List<Salesman> entities = entityPage.getContent();
        return new PageImpl<>(salesmanMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public SalesmanDto update(SalesmanDto salesmanDto, Long id) {
        SalesmanDto data = findById(id);
        Salesman entity = salesmanMapper.toEntity(salesmanDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}