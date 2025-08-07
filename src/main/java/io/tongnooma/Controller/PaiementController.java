package io.tongnooma.Controller;

import io.tongnooma.Dto.PaiementRequestDTO;
import io.tongnooma.Dto.PaiementResponseDTO;
import io.tongnooma.Service.PaiementServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paiements")
public class PaiementController {

    private final PaiementServiceInterface paiementService;

    // Créer un paiement
    @PostMapping
    public ResponseEntity<PaiementResponseDTO> create(@RequestBody PaiementRequestDTO dto) {
        PaiementResponseDTO response = paiementService.create(dto);
        return ResponseEntity.ok(response);
    }

    //Modifier un paiement
    @PutMapping("/{id}")
    public ResponseEntity<PaiementResponseDTO> update(@PathVariable Long id, @RequestBody PaiementRequestDTO dto) {
        PaiementResponseDTO response = paiementService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    // Supprimer un paiement
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paiementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Obtenir un paiement par ID
    @GetMapping("/{id}")
    public ResponseEntity<PaiementResponseDTO> getById(@PathVariable Long id) {
        PaiementResponseDTO response = paiementService.getById(id);
        return ResponseEntity.ok(response);
    }

    // Lister tous les paiements
    @GetMapping
    public ResponseEntity<List<PaiementResponseDTO>> getAll() {
        List<PaiementResponseDTO> paiements = paiementService.getAll();
        return ResponseEntity.ok(paiements);
    }

}
