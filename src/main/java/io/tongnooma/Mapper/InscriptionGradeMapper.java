package io.tongnooma.Mapper;

import io.tongnooma.Dto.InscriptionGradeRequestDTO;
import io.tongnooma.Dto.InscriptionGradeResponseDTO;
import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import org.springframework.stereotype.Component;

@Component
public class InscriptionGradeMapper {

    /**Convertit une entité en DTO de réponse.*/
    public InscriptionGradeResponseDTO toDTO(InscriptionGradeJPAEntity entity) {
        InscriptionGradeResponseDTO dto = new InscriptionGradeResponseDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setAge(entity.getAge());
        dto.setGradeActuel(entity.getGradeActuel());
        dto.setNomClub(entity.getNomClub());
        dto.setDateSoumission(entity.getDateSoumission());
        dto.setPhoto(entity.getPhoto());
        dto.setStatut(entity.getStatut());

        if (entity.getUtilisateur() != null) {
            dto.setUtilisateurId(entity.getUtilisateur().getId());
        }

        return dto;
    }

    /** Convertit un DTO de requête en entité.*/
    public InscriptionGradeJPAEntity toEntity(InscriptionGradeRequestDTO dto) {
        return InscriptionGradeJPAEntity.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .age(dto.getAge())
                .gradeActuel(dto.getGradeActuel())
                .nomClub(dto.getNomClub())
                .dateSoumission(dto.getDateSoumission())
                .photo(dto.getPhoto())
                .statut(dto.getStatut())
                .build();
    }

    /** Associe un utilisateur à la demande.*/
    public void attachUtilisateur(InscriptionGradeJPAEntity inscription, UtilisateurJPAEntity utilisateur) {
        inscription.setUtilisateur(utilisateur);
    }
}
