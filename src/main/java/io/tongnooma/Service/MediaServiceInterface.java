package io.tongnooma.Service;

import io.tongnooma.Dto.MediaRequestDTO;
import io.tongnooma.Dto.MediaResponseDTO;

import java.util.List;

public interface MediaServiceInterface {

    MediaResponseDTO create(MediaRequestDTO dto);

    MediaResponseDTO update(Long id, MediaRequestDTO dto);

    void delete(Long id);

    MediaResponseDTO getById(Long id);

    List<MediaResponseDTO> getAll();
}
