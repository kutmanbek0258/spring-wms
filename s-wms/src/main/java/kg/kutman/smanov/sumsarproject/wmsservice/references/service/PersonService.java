package kg.kutman.smanov.sumsarproject.wmsservice.references.service;

import cn.hutool.core.bean.BeanUtil;
import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.PersonDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.mapper.PersonMapper;
import kg.kutman.smanov.sumsarproject.wmsservice.references.models.Person;
import kg.kutman.smanov.sumsarproject.wmsservice.references.repository.PersonRepository;
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
public class PersonService {
    private final PersonRepository repository;
    private final PersonMapper personMapper;
    private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

    public PersonService(PersonRepository repository, PersonMapper personMapper) {
        this.repository = repository;
        this.personMapper = personMapper;
    }

    public PersonDto save(PersonDto personDto) {
        Person entity = personMapper.toEntity(personDto);
        logger.info(entity.toString());
        return personMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PersonDto findById(Long id) {
        return personMapper.toDto(repository.findById(id).orElseThrow());
    }

    public Page<PersonDto> findByCondition(PersonDto personDto, Pageable pageable) {
        Page<Person> entityPage = repository.findAll(pageable);
        List<Person> entities = entityPage.getContent();
        return new PageImpl<>(personMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PersonDto update(PersonDto personDto, Long id) {
        PersonDto data = findById(id);
        Person entity = personMapper.toEntity(personDto);
        BeanUtil.copyProperties(entity, data, "id");
        return save(data);
    }
}