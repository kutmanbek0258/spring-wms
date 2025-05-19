package kg.kutman.smanov.sumsarproject.wmsservice.documents.controller;

import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.ReceiptItemDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.service.ReceiptItemService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/receipt-item")
@RestController
@Slf4j
public class ReceiptItemController {
    private final ReceiptItemService receiptItemService;

    public ReceiptItemController(ReceiptItemService receiptItemService) {
        this.receiptItemService = receiptItemService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated ReceiptItemDto receiptItemDto) {
        receiptItemService.save(receiptItemDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReceiptItemDto> findById(@PathVariable("id") Long id) {
        ReceiptItemDto receiptItem = receiptItemService.findById(id);
        return ResponseEntity.ok(receiptItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(receiptItemService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        receiptItemService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<ReceiptItemDto>> pageQuery(@RequestParam Long receiptId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC)
                                                              Pageable pageable) {
        Page<ReceiptItemDto> receiptItemPage = receiptItemService.findByCondition(receiptId, pageable);
        return ResponseEntity.ok(receiptItemPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated ReceiptItemDto receiptItemDto, @PathVariable("id") Long id) {
        receiptItemService.update(receiptItemDto, id);
        return ResponseEntity.ok().build();
    }
}