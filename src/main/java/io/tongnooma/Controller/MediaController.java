package io.tongnooma.Controller;

import io.tongnooma.Dto.MediaRequestDTO;
import io.tongnooma.Dto.MediaResponseDTO;
import io.tongnooma.Service.MediaServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/media")
public class MediaController {

    private final MediaServiceInterface service;

    @PostMapping
    public ResponseEntity<MediaResponseDTO> create(@RequestBody MediaRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MediaResponseDTO> update(@PathVariable Long id, @RequestBody MediaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MediaResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MediaResponseDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}
