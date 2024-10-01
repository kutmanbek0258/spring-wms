package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.ProductGroupMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.ProductGroupDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.ProductGroup;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.ProductGroupRepository;
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
public class ProductGroupService {
    private final ProductGroupRepository repository;
    private final ProductGroupMapper productGroupMapper;

    public ProductGroupService(ProductGroupRepository repository, ProductGroupMapper productGroupMapper) {
        this.repository = repository;
        this.productGroupMapper = productGroupMapper;
    }

    public ProductGroupDto save(ProductGroupDto productGroupDto) {
        ProductGroup entity = productGroupMapper.toEntity(productGroupDto);
        return productGroupMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ProductGroupDto findById(Long id) {
        return productGroupMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<ProductGroupDto> findByCondition(ProductGroupDto productGroupDto, Pageable pageable) {
        Page<ProductGroup> entityPage = repository.findAll(pageable);
        List<ProductGroup> entities = entityPage.getContent();
        return new PageImpl<>(productGroupMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public ProductGroupDto update(ProductGroupDto productGroupDto, Long id) {
        ProductGroupDto data = findById(id);
        ProductGroup entity = productGroupMapper.toEntity(productGroupDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}