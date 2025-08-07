package io.tongnooma.Controller;

import io.tongnooma.Dto.InscriptionGradeRequestDTO;
import io.tongnooma.Dto.InscriptionGradeResponseDTO;
import io.tongnooma.Service.InscriptionGradeServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inscriptions")
public class InscriptionGradeController {

    private final InscriptionGradeServiceInterface service;

    @PostMapping
    public ResponseEntity<InscriptionGradeResponseDTO> create(@RequestBody InscriptionGradeRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InscriptionGradeResponseDTO> update(@PathVariable Long id, @RequestBody InscriptionGradeRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscriptionGradeResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<InscriptionGradeResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<InscriptionGradeResponseDTO>> getByStatut(@PathVariable String statut) {
        return ResponseEntity.ok(service.getByStatut(statut));
    }
}
