package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.PriceTemplateDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.PriceTemplateMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.PriceTemplate;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.PriceTemplateRepository;
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
public class PriceTemplateService {
    private final PriceTemplateRepository repository;
    private final PriceTemplateMapper priceTemplateMapper;

    public PriceTemplateService(PriceTemplateRepository repository, PriceTemplateMapper priceTemplateMapper) {
        this.repository = repository;
        this.priceTemplateMapper = priceTemplateMapper;
    }

    public PriceTemplateDto save(PriceTemplateDto priceTemplateDto) {
        PriceTemplate entity = priceTemplateMapper.toEntity(priceTemplateDto);
        return priceTemplateMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PriceTemplateDto findById(Long id) {
        return priceTemplateMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<PriceTemplateDto> findByCondition(PriceTemplateDto priceTemplateDto, Pageable pageable) {
        Page<PriceTemplate> entityPage = repository.findAll(pageable);
        List<PriceTemplate> entities = entityPage.getContent();
        return new PageImpl<>(priceTemplateMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PriceTemplateDto update(PriceTemplateDto priceTemplateDto, Long id) {
        PriceTemplateDto data = findById(id);
        PriceTemplate entity = priceTemplateMapper.toEntity(priceTemplateDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}