package io.tongnooma.Persistance;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table(name = "enseignant")
public class EnseignantJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String grade;
    private String biographie;
    private String photoUrl;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    private UtilisateurJPAEntity utilisateur;
}
