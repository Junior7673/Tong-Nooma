package io.tongnooma.Mapper;

import io.tongnooma.Dto.EnseignantRequestDTO;
import io.tongnooma.Dto.EnseignantResponseDTO;
import io.tongnooma.Persistance.EnseignantJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import org.springframework.stereotype.Component;

@Component
public class EnseignantMapper {

    /** Convertit une entité en DTO de réponse*/
    public EnseignantResponseDTO toDTO(EnseignantJPAEntity entity) {
        EnseignantResponseDTO dto = new EnseignantResponseDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setGrade(entity.getGrade());
        dto.setBiographie(entity.getBiographie());
        dto.setPhotoUrl(entity.getPhotoUrl());

        if (entity.getUtilisateur() != null) {
            dto.setUtilisateurId(entity.getUtilisateur().getId());
        }

        return dto;
    }

    /** Convertit un DTO de requête en entité (sans setter l'utilisateur ici).*/
    public EnseignantJPAEntity toEntity(EnseignantRequestDTO dto) {
        return EnseignantJPAEntity.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .grade(dto.getGrade())
                .biographie(dto.getBiographie())
                .photoUrl(dto.getPhotoUrl())
                .build();
    }

    /** Associe un utilisateur à l'entité après construction (optionnel)*/
    public void attachUtilisateur(EnseignantJPAEntity enseignant, UtilisateurJPAEntity utilisateur) {
        enseignant.setUtilisateur(utilisateur);
    }
}
