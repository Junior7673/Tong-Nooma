package io.tongnooma.Service;

import io.tongnooma.Dto.UtilisateurRequestDTO;
import io.tongnooma.Dto.UtilisateurResponseDTO;

import java.util.List;

public interface UtilisateurServiceInterface {
    UtilisateurResponseDTO create(UtilisateurRequestDTO dto);

    UtilisateurResponseDTO update(Long id, UtilisateurRequestDTO dto);

    void delete(Long id);

    UtilisateurResponseDTO getById(Long id);

    List<UtilisateurResponseDTO> getAll();
}
