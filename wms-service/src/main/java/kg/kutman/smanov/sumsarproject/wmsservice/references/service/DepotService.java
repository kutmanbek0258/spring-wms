package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Depot;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.DepotRepository;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.DepotDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.DepotMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class DepotService {
    private final DepotRepository repository;
    private final DepotMapper depotMapper;

    public DepotService(DepotRepository repository, DepotMapper depotMapper) {
        this.repository = repository;
        this.depotMapper = depotMapper;
    }

    public DepotDto save(DepotDto depotDto) {
        Depot entity = depotMapper.toEntity(depotDto);
        return depotMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public DepotDto findById(Long id) {
        return depotMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<DepotDto> findByCondition(DepotDto depotDto, Pageable pageable) {
        Page<Depot> entityPage = repository.findAll(pageable);
        List<Depot> entities = entityPage.getContent();
        return new PageImpl<>(depotMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public DepotDto update(DepotDto depotDto, Long id) {
        DepotDto data = findById(id);
        Depot entity = depotMapper.toEntity(depotDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}