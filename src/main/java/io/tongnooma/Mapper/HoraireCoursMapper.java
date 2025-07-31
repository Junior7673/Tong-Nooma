package io.tongnooma.Mapper;

import io.tongnooma.Dto.HoraireCoursRequestDTO;
import io.tongnooma.Dto.HoraireCoursResponseDTO;
import io.tongnooma.Persistance.HoraireCoursJPAEntity;
import org.springframework.stereotype.Component;

@Component
public class HoraireCoursMapper {

    /**
     * Convertit une entité JPA vers un DTO de réponse.
     */
    public HoraireCoursResponseDTO toDTO(HoraireCoursJPAEntity entity) {
        HoraireCoursResponseDTO dto = new HoraireCoursResponseDTO();
        dto.setId(entity.getId());
        dto.setJour(entity.getJour());
        dto.setHeureDebut(entity.getHeureDebut());
        dto.setHeureFin(entity.getHeureFin());
        dto.setLieu(entity.getLieu());
        return dto;
    }

    /**
     * Convertit un DTO de requête vers une entité JPA.
     */
    public HoraireCoursJPAEntity toEntity(HoraireCoursRequestDTO dto) {
        return HoraireCoursJPAEntity.builder()
                .id(dto.getId())
                .jour(dto.getJour())
                .heureDebut(dto.getHeureDebut())
                .heureFin(dto.getHeureFin())
                .lieu(dto.getLieu())
                .build();
    }
}
