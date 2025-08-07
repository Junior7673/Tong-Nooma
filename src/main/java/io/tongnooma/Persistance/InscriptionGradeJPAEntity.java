package io.tongnooma.Persistance;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "incriptionGrade")
public class InscriptionGradeJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private int age;
    private String gradeActuel;
    private String nomClub;
    private Date dateSoumission;
    private String photo;

    @Enumerated(EnumType.STRING)
    private StatutEnum statut;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private UtilisateurJPAEntity utilisateur;

    @OneToOne(mappedBy = "inscription", cascade = CascadeType.ALL)
    private PaiementJPAEntity paiement;

    public enum StatutEnum{
        EN_ATTENTE,
        VALIDE,
        REFUSE
    }
}
