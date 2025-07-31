package io.tongnooma.Dto;

import io.tongnooma.Persistance.PaiementJPAEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaiementRequestDTO {

    /** DTO pour créer ou modifier un paiement.*/
    private Long id; // pour différencier création vs mise à jour

    private double montant;

    private PaiementJPAEntity.ModePaiementEnum mode;

    private LocalDate datePaiement;

    private String justificatif;

    private Long inscriptionId; // lien vers l’inscription concernée
}

