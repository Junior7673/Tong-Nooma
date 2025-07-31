package io.tongnooma.Dto;

import io.tongnooma.Persistance.PaiementJPAEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaiementResponseDTO {
    /** DTO utilisé pour exposer les paiements.*/
    private Long id;

    private double montant;

    private PaiementJPAEntity.ModePaiementEnum mode;

    private LocalDate datePaiement;

    private String justificatif;

    private Long inscriptionId; // référence utile pour le front
}
