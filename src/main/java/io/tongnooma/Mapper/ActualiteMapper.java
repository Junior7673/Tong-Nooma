package io.tongnooma.Mapper;

import io.tongnooma.Dto.ActualiteRequestDTO;
import io.tongnooma.Dto.ActualiteResponseDTO;
import io.tongnooma.Persistance.ActualiteJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import org.springframework.stereotype.Component;

@Component
public class ActualiteMapper {

    /** Convertit une entité JPA en DTO de réponse.*/
    public ActualiteResponseDTO toDTO(ActualiteJPAEntity entity) {
        ActualiteResponseDTO dto = new ActualiteResponseDTO();
        dto.setId(entity.getId());
        dto.setTitre(entity.getTitre());
        dto.setContenu(entity.getContenu());
        dto.setImageUrl(entity.getImageUrl());
        dto.setDatePub(entity.getDatePub());

        if (entity.getAuteur() != null) {
            dto.setAuteurNom(entity.getAuteur().getNom() + " " + entity.getAuteur().getPrenom());
        }

        return dto;
    }

    /**Convertit un DTO de requête en entité (sans lier l'auteur directement ici).*/
    public ActualiteJPAEntity toEntity(ActualiteRequestDTO dto) {
        return ActualiteJPAEntity.builder()
                .id(dto.getId()) // important si mise à jour
                .titre(dto.getTitre())
                .contenu(dto.getContenu())
                .imageUrl(dto.getImageUrl())
                .datePub(dto.getDatePub())
                .build();
    }

    /** Lier manuellement l’auteur si nécessaire dans le service.*/
    public void attachAuteur(ActualiteJPAEntity entity, UtilisateurJPAEntity auteur) {
        entity.setAuteur(auteur);
    }
}
