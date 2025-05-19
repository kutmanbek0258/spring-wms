package kg.kutman.smanov.sumsarproject.wmsservice.references.controller;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.SupplierDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.service.SupplierService;
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

@RequestMapping("/api/supplier")
@RestController
@Slf4j
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated SupplierDto supplierDto) {
        supplierService.save(supplierDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> findById(@PathVariable("id") Long id) {
        SupplierDto supplier = supplierService.findById(id);
        return ResponseEntity.ok(supplier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(supplierService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        supplierService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<SupplierDto>> pageQuery(SupplierDto supplierDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<SupplierDto> supplierPage = supplierService.findByCondition(supplierDto, pageable);
        return ResponseEntity.ok(supplierPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated SupplierDto supplierDto, @PathVariable("id") Long id) {
        supplierService.update(supplierDto, id);
        return ResponseEntity.ok().build();
    }
}