package kg.kutman.smanov.sumsarproject.wmsservice.documents.controller;

import java.util.Optional;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.WriteOffItemDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.service.WriteOffItemService;
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

@RequestMapping("/api/write-off-item")
@RestController
@Slf4j
public class WriteOffItemController {
    private final WriteOffItemService writeOffItemService;

    public WriteOffItemController(WriteOffItemService writeOffItemService) {
        this.writeOffItemService = writeOffItemService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated WriteOffItemDto writeOffItemDto) {
        writeOffItemService.save(writeOffItemDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriteOffItemDto> findById(@PathVariable("id") Long id) {
        WriteOffItemDto writeOffItem = writeOffItemService.findById(id);
        return ResponseEntity.ok(writeOffItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(writeOffItemService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        writeOffItemService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<WriteOffItemDto>> pageQuery(@RequestParam Long writeOffId, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<WriteOffItemDto> writeOffItemPage = writeOffItemService.findByCondition(writeOffId, pageable);
        return ResponseEntity.ok(writeOffItemPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated WriteOffItemDto writeOffItemDto, @PathVariable("id") Long id) {
        writeOffItemService.update(writeOffItemDto, id);
        return ResponseEntity.ok().build();
    }
}