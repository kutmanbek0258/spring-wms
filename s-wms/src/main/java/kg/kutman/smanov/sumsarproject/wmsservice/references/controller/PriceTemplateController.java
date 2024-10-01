package kg.kutman.smanov.sumsarproject.wmsservice.references.controller;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.PriceTemplateDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.service.PriceTemplateService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
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

@RequestMapping("/api/price-template")
@RestController
@Slf4j
public class PriceTemplateController {
    private final PriceTemplateService priceTemplateService;

    public PriceTemplateController(PriceTemplateService priceTemplateService) {
        this.priceTemplateService = priceTemplateService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PriceTemplateDto priceTemplateDto) {
        priceTemplateService.save(priceTemplateDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceTemplateDto> findById(@PathVariable("id") Long id) {
        PriceTemplateDto priceTemplate = priceTemplateService.findById(id);
        return ResponseEntity.ok(priceTemplate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(priceTemplateService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        priceTemplateService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<PriceTemplateDto>> pageQuery(PriceTemplateDto priceTemplateDto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PriceTemplateDto> priceTemplatePage = priceTemplateService.findByCondition(priceTemplateDto, pageable);
        return ResponseEntity.ok(priceTemplatePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PriceTemplateDto priceTemplateDto, @PathVariable("id") Long id) {
        priceTemplateService.update(priceTemplateDto, id);
        return ResponseEntity.ok().build();
    }
}