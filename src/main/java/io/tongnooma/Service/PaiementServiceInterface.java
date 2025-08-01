package io.tongnooma.Service;

import io.tongnooma.Dto.PaiementRequestDTO;
import io.tongnooma.Dto.PaiementResponseDTO;

import java.util.List;

public interface PaiementServiceInterface {
    PaiementResponseDTO create(PaiementRequestDTO dto);

    PaiementResponseDTO update(Long id, PaiementRequestDTO dto);

    void delete(Long id);

    PaiementResponseDTO getById(Long id);

    List<PaiementResponseDTO> getAll();
}
