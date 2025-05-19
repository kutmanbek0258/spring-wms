package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.CompanyDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.CompanyMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Company;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper companyMapper;
    private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);

    public CompanyService(CompanyRepository repository, CompanyMapper companyMapper) {
        this.repository = repository;
        this.companyMapper = companyMapper;
    }

    public CompanyDto save(CompanyDto companyDto) {
        Company entity = companyMapper.toEntity(companyDto);
        return companyMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CompanyDto findById(Long id) {
        return companyMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<CompanyDto> findByCondition(CompanyDto companyDto, Pageable pageable) {
        Page<Company> entityPage = repository.findAll(pageable);
        List<Company> entities = entityPage.getContent();
        return new PageImpl<>(companyMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CompanyDto update(CompanyDto companyDto, Long id) {
        CompanyDto data = findById(id);
        Company entity = companyMapper.toEntity(companyDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}