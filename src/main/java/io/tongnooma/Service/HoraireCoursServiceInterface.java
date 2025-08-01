package io.tongnooma.Service;

import io.tongnooma.Dto.HoraireCoursRequestDTO;
import io.tongnooma.Dto.HoraireCoursResponseDTO;

import java.util.List;

public interface HoraireCoursServiceInterface {

    HoraireCoursResponseDTO create(HoraireCoursRequestDTO dto);

    HoraireCoursResponseDTO update(Long id, HoraireCoursRequestDTO dto);

    void delete(Long id);

    HoraireCoursResponseDTO getById(Long id);

    List<HoraireCoursResponseDTO> getAll();
}
