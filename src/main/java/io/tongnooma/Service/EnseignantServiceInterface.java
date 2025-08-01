package io.tongnooma.Service;

import io.tongnooma.Dto.EnseignantRequestDTO;
import io.tongnooma.Dto.EnseignantResponseDTO;

import java.util.List;

public interface EnseignantServiceInterface {
    EnseignantResponseDTO create(EnseignantRequestDTO dto);

    EnseignantResponseDTO update(Long id, EnseignantRequestDTO dto);

    void delete(Long id);

    EnseignantResponseDTO getById(Long id);

    List<EnseignantResponseDTO> getAll();
}
