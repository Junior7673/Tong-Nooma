package io.tongnooma.Mapper;

import io.tongnooma.Dto.MediaRequestDTO;
import io.tongnooma.Dto.MediaResponseDTO;
import io.tongnooma.Persistance.EnseignantJPAEntity;
import io.tongnooma.Persistance.MediaJPAEntity;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

    /** Convertit une entité Media en DTO de réponse.*/
    public MediaResponseDTO toDTO(MediaJPAEntity entity) {
        MediaResponseDTO dto = new MediaResponseDTO();
        dto.setId(entity.getId());
        dto.setType(entity.getType());
        dto.setUrl(entity.getUrl());
        dto.setDescription(entity.getDescription());
        dto.setDateAjout(entity.getDateAjout());

        return dto;
    }

    /**
     * Convertit un DTO de requête en entité JPA.
     */
    public MediaJPAEntity toEntity(MediaRequestDTO dto) {
        return MediaJPAEntity.builder()
                .id(dto.getId())
                .type(dto.getType())
                .url(dto.getUrl())
                .description(dto.getDescription())
                .dateAjout(dto.getDateAjout())
                .build();
    }
}
