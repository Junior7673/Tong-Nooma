package io.tongnooma.Persistance;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name="utilisateur")

public class UtilisateurJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private LocalDateTime dateInscrip;

    @OneToOne(mappedBy = "utilisateur", cascade = CascadeType.ALL)
    private EnseignantJPAEntity enseignant;

    @OneToMany(mappedBy = "auteur")
    private List<ActualiteJPAEntity> actualite;

    @OneToMany(mappedBy = "utilisateur")
    private List<InscriptionGradeJPAEntity> inscription;

    public enum RoleEnum{
        ADMIN,
        UTILISATEUR
    }

}
