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
        dto.setDiscipline(entity.getDiscipline());
        dto.setDateSoumission(entity.getDateSoumission());
        dto.setPhoto(entity.getPhoto());
        dto.setStatut(entity.getStatut());

        if (entity.getUtilisateur() != null) {
            dto.setUtilisateurNom(entity.getUtilisateur().getNom());
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
                .discipline(dto.getDiscipline())
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
