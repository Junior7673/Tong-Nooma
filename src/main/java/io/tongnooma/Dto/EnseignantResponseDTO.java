package io.tongnooma.Dto;

import lombok.Data;

@Data
public class EnseignantResponseDTO {
    /** DTO utilisé pour exposer les informations d’un enseignant.*/
    private Long id;

    private String nom;

    private String prenom;

    private String grade;

    private String biographie;

    private String photoUrl;

    private Long utilisateurId;
}
