package io.tongnooma.Controller;

import io.tongnooma.Dto.EnseignantRequestDTO;
import io.tongnooma.Dto.EnseignantResponseDTO;
import io.tongnooma.Service.EnseignantServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enseignants")
@CrossOrigin(origins = "http://localhost:4200")

public class EnseignantController {

    private final EnseignantServiceInterface service;

    @PostMapping
    public ResponseEntity<EnseignantResponseDTO> create(@RequestBody EnseignantRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnseignantResponseDTO> update(@PathVariable Long id, @RequestBody EnseignantRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<EnseignantResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
