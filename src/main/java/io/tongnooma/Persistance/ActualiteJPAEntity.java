package io.tongnooma.Persistance;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="actualite")
public class ActualiteJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;

    //Contenu principal de l’actualité (texte HTML ou markdown).
    @Column(columnDefinition = "TEXT")
    private String contenu;
    private String imageUrl;
    private LocalDateTime datePub;

    @ManyToOne
    @JoinColumn(name = "auteur_id")
    private UtilisateurJPAEntity auteur;
}
