package io.tongnooma.Service;

import io.tongnooma.Dto.InscriptionGradeRequestDTO;
import io.tongnooma.Dto.InscriptionGradeResponseDTO;

import java.util.List;

public interface InscriptionGradeServiceInterface {

    InscriptionGradeResponseDTO create(InscriptionGradeRequestDTO dto);

    InscriptionGradeResponseDTO update(Long id, InscriptionGradeRequestDTO dto);

    void delete(Long id);

    InscriptionGradeResponseDTO getById(Long id);

    List<InscriptionGradeResponseDTO> getAll();

    List<InscriptionGradeResponseDTO> getByStatut(String statut);
}
