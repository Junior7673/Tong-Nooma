package io.tongnooma.Dto;

import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import lombok.Data;

import java.time.LocalDate;
@Data
public class InscriptionGradeRequestDTO {
    /** DTO pour soumettre ou modifier une demande de passage de grade.*/
    private Long id;

    private String nom;

    private String prenom;

    private int age;

    private String gradeActuel;

    private String discipline;

    private LocalDate dateSoumission;

    private String photo;

    private InscriptionGradeJPAEntity.StatutEnum statut;

    private Long utilisateurId; // auteur de la demande (peut être null pour un invité)
}
