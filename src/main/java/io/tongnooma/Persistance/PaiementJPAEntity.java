package io.tongnooma.Persistance;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "paiement")
public class PaiementJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double montant;

    @Enumerated(EnumType.STRING)
    private ModePaiementEnum mode;

    private LocalDate datePaiement;
    private String justificatif;

    @OneToOne
    @JoinColumn(name = "inscription_id")
    private InscriptionGradeJPAEntity inscription;

    public enum ModePaiementEnum{
        EN_LIGNE,
        MANUEL

    }
}
