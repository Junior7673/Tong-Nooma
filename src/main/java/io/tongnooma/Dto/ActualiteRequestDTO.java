package io.tongnooma.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActualiteRequestDTO {
    /**
     * DTO utilisé pour créer ou modifier une actualité.
     * Contient l'id de l'auteur (utilisateur) et l'id de l'actualité.
     */
    private Long id;

    private String titre;

    private String contenu;

    private String imageUrl;

    private LocalDateTime datePub;

    private Long auteurId;
}
