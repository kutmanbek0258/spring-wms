package kg.kutman.smanov.sumsarproject.wmsservice.documents.controller;

import java.util.Optional;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.dto.WriteOffDto;
import kg.kutman.smanov.sumsarproject.wmsservice.documents.service.WriteOffService;
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

@RequestMapping("/api/write-off")
@RestController
@Slf4j
public class WriteOffController {
    private final WriteOffService writeOffService;

    public WriteOffController(WriteOffService writeOffService) {
        this.writeOffService = writeOffService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated WriteOffDto writeOffDto) {
        writeOffService.save(writeOffDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WriteOffDto> findById(@PathVariable("id") Long id) {
        WriteOffDto writeOff = writeOffService.findById(id);
        return ResponseEntity.ok(writeOff);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(writeOffService.findById(id)).orElseThrow(() -> {
            log.error("Unable to delete non-existent data！");
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        writeOffService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<WriteOffDto>> pageQuery(WriteOffDto writeOffDto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<WriteOffDto> writeOffPage = writeOffService.findByCondition(writeOffDto, pageable);
        return ResponseEntity.ok(writeOffPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated WriteOffDto writeOffDto, @PathVariable("id") Long id) {
        writeOffService.update(writeOffDto, id);
        return ResponseEntity.ok().build();
    }
}