package kg.kutman.smanov.sumsarproject.wmsservice.references.controller;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.CompanyDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.service.CompanyService;
import java.util.Optional;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/company")
@RestController
public class CompanyController {
    private final CompanyService companyService;
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated CompanyDto companyDto) {
        companyService.save(companyDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> findById(@PathVariable("id") Long id) {
        CompanyDto company = companyService.findById(id);
        return ResponseEntity.ok(company);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(companyService.findById(id)).orElseThrow(() -> {
            return new ResourceNotFoundException("Company not found!");
        });
        companyService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<CompanyDto>> pageQuery(CompanyDto companyDto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CompanyDto> companyPage = companyService.findByCondition(companyDto, pageable);
        return ResponseEntity.ok(companyPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated CompanyDto companyDto, @PathVariable("id") Long id) {
        companyService.update(companyDto, id);
        return ResponseEntity.ok().build();
    }
}