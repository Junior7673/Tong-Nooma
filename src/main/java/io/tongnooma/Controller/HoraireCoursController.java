package io.tongnooma.Controller;

import io.tongnooma.Dto.HoraireCoursRequestDTO;
import io.tongnooma.Dto.HoraireCoursResponseDTO;
import io.tongnooma.Service.HoraireCoursServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/horaires")
@CrossOrigin(origins = "http://localhost:4200")

public class HoraireCoursController {

    private final HoraireCoursServiceInterface horaireService;

    // Créer un horaire
    @PostMapping
    public ResponseEntity<HoraireCoursResponseDTO> create(@RequestBody HoraireCoursRequestDTO dto) {
        HoraireCoursResponseDTO response = horaireService.create(dto);
        return ResponseEntity.ok(response);
    }

    // Mettre à jour un horaire
    @PutMapping("/{id}")
    public ResponseEntity<HoraireCoursResponseDTO> update(@PathVariable Long id, @RequestBody HoraireCoursRequestDTO dto) {
        HoraireCoursResponseDTO response = horaireService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    // Supprimer un horaire
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        horaireService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // Obtenir un horaire par ID
    @GetMapping("/{id}")
    public ResponseEntity<HoraireCoursResponseDTO> getById(@PathVariable Long id) {
        HoraireCoursResponseDTO response = horaireService.getById(id);
        return ResponseEntity.ok(response);
    }

    // Lister tous les horaires
    @GetMapping
    public ResponseEntity<List<HoraireCoursResponseDTO>> getAll() {
        List<HoraireCoursResponseDTO> horaires = horaireService.getAll();
        return ResponseEntity.ok(horaires);
    }
}
