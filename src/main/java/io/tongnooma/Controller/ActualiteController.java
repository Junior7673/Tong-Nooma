package io.tongnooma.Controller;

import io.tongnooma.Dto.ActualiteRequestDTO;
import io.tongnooma.Dto.ActualiteResponseDTO;
import io.tongnooma.Service.ActualiteServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping("/actualite")
public class ActualiteController {

    private final ActualiteServiceInterface service;

    @PostMapping
    public ResponseEntity<ActualiteResponseDTO> create(@RequestBody ActualiteRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActualiteResponseDTO> update(@PathVariable Long id, @RequestBody ActualiteRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActualiteResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ActualiteResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
