package kg.kutman.smanov.sumsarproject.wmsservice.documents.controller;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.ReceiptDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.service.ReceiptService;
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

@RequestMapping("/api/receipt")
@RestController
@Slf4j
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ReceiptDto receiptDto) {
        receiptService.save(receiptDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptDto> findById(@PathVariable("id") Long id) {
        ReceiptDto receipt = receiptService.findById(id);
        return ResponseEntity.ok(receipt);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(receiptService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        receiptService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ReceiptDto>> pageQuery(ReceiptDto receiptDto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ReceiptDto> receiptPage = receiptService.findByCondition(receiptDto, pageable);
        return ResponseEntity.ok(receiptPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ReceiptDto receiptDto, @PathVariable("id") Long id) {
        receiptService.update(receiptDto, id);
        return ResponseEntity.ok().build();
    }
}