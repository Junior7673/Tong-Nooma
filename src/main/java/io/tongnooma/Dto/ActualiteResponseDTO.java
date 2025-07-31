package io.tongnooma.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActualiteResponseDTO {

    /** DTO utilisé pour exposer les actualités côté front-end.*/
    private Long id;

    private String titre;

    private String contenu;

    private String imageUrl;

    private LocalDateTime datePub;

    private String auteurNom; // lisible pour l'affichage
}
