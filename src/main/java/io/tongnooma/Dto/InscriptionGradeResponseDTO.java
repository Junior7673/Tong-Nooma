package io.tongnooma.Dto;

import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InscriptionGradeResponseDTO {
    /** DTO renvoyé en réponse pour une inscription à un grade.*/
    private Long id;

    private String nom;

    private String prenom;

    private int age;

    private String gradeActuel;

    private String discipline;

    private LocalDate dateSoumission;

    private String photo;

    private InscriptionGradeJPAEntity.StatutEnum statut;

    private String utilisateurNom; // pour affichage lisible
}
