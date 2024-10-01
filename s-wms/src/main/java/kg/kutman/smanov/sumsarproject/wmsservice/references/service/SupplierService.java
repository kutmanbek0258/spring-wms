package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.SupplierDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.SupplierMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Supplier;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.SupplierRepository;
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
public class SupplierService {
    private final SupplierRepository repository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository repository, SupplierMapper supplierMapper) {
        this.repository = repository;
        this.supplierMapper = supplierMapper;
    }

    public SupplierDto save(SupplierDto supplierDto) {
        Supplier entity = supplierMapper.toEntity(supplierDto);
        return supplierMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public SupplierDto findById(Long id) {
        return supplierMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<SupplierDto> findByCondition(SupplierDto supplierDto, Pageable pageable) {
        Page<Supplier> entityPage = repository.findAll(pageable);
        List<Supplier> entities = entityPage.getContent();
        return new PageImpl<>(supplierMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public SupplierDto update(SupplierDto supplierDto, Long id) {
        SupplierDto data = findById(id);
        Supplier entity = supplierMapper.toEntity(supplierDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}