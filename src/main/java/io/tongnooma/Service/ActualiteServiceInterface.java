package io.tongnooma.Service;

import io.tongnooma.Dto.ActualiteRequestDTO;
import io.tongnooma.Dto.ActualiteResponseDTO;

import java.util.List;

public interface ActualiteServiceInterface {

    ActualiteResponseDTO create(ActualiteRequestDTO dto);

    ActualiteResponseDTO update(Long id, ActualiteRequestDTO dto);

    void delete(Long id);

    ActualiteResponseDTO getById(Long id);

    List<ActualiteResponseDTO> getAll();
}
