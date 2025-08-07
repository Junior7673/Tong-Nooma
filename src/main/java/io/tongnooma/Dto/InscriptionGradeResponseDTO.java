package io.tongnooma.Dto;

import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import lombok.Data;

import java.util.Date;

@Data
public class InscriptionGradeResponseDTO {
    /** DTO renvoyé en réponse pour une inscription à un grade.*/
    private Long id;

    private String nom;

    private String prenom;

    private int age;

    private String gradeActuel;

    private String nomClub;

    private Date dateSoumission;

    private String photo;

    private InscriptionGradeJPAEntity.StatutEnum statut;

    private Long utilisateurId; // pour affichage lisible
}
