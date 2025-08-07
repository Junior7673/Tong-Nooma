package io.tongnooma.Controller;

import io.tongnooma.Dto.UtilisateurRequestDTO;
import io.tongnooma.Dto.UtilisateurResponseDTO;
import io.tongnooma.Service.UtilisateurServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/utilisateurs")
@CrossOrigin(origins = "http://localhost:4200")

public class UtilisateurController {

    private final UtilisateurServiceInterface service;

    /** Crée un nouvel utilisateur.*/
    @PostMapping
    public ResponseEntity<UtilisateurResponseDTO> create(@RequestBody UtilisateurRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    /** Met à jour un utilisateur existant.*/
    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDTO> update(@PathVariable Long id, @RequestBody UtilisateurRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    /**
     * Supprime un utilisateur par ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Récupère un utilisateur par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    /**
     * Récupère tous les utilisateurs.
     */
    @GetMapping
    public ResponseEntity<List<UtilisateurResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
