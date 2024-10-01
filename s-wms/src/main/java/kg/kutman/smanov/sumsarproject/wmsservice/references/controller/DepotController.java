package kg.kutman.smanov.sumsarproject.wmsservice.references.controller;

import kg.kutman.smanov.sumsarproject.wmsservice.references.dto.DepotDto;
import kg.kutman.smanov.sumsarproject.wmsservice.references.service.DepotService;
import java.util.Optional;
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

@RequestMapping("/api/depot")
@RestController
public class DepotController {
    private final DepotService depotService;

    public DepotController(DepotService depotService) {
        this.depotService = depotService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated DepotDto depotDto) {
        depotService.save(depotDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepotDto> findById(@PathVariable("id") Long id) {
        DepotDto depot = depotService.findById(id);
        return ResponseEntity.ok(depot);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        Optional.ofNullable(depotService.findById(id)).orElseThrow(() -> {
            return new ResourceNotFoundException("Unable to delete non-existent data！");
        });
        depotService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<DepotDto>> pageQuery(DepotDto depotDto, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DepotDto> depotPage = depotService.findByCondition(depotDto, pageable);
        return ResponseEntity.ok(depotPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated DepotDto depotDto, @PathVariable("id") Long id) {
        depotService.update(depotDto, id);
        return ResponseEntity.ok().build();
    }
}