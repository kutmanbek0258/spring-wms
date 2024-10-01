package kg.kutman.smanov.sumsarproject.wmsservice.references.controller;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.ProductGroupDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.service.ProductGroupService;
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

@RequestMapping("/api/product-group")
@RestController
@Slf4j
public class ProductGroupController {
    private final ProductGroupService productGroupService;

    public ProductGroupController(ProductGroupService productGroupService) {
        this.productGroupService = productGroupService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ProductGroupDto productGroupDto) {
        productGroupService.save(productGroupDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductGroupDto> findById(@PathVariable("id") Long id) {
        ProductGroupDto productGroup = productGroupService.findById(id);
        return ResponseEntity.ok(productGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(productGroupService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        productGroupService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ProductGroupDto>> pageQuery(ProductGroupDto productGroupDto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductGroupDto> productGroupPage = productGroupService.findByCondition(productGroupDto, pageable);
        return ResponseEntity.ok(productGroupPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ProductGroupDto productGroupDto, @PathVariable("id") Long id) {
        productGroupService.update(productGroupDto, id);
        return ResponseEntity.ok().build();
    }
}