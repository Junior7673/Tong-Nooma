package io.tongnooma.Mapper;

import io.tongnooma.Dto.PaiementRequestDTO;
import io.tongnooma.Dto.PaiementResponseDTO;
import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import io.tongnooma.Persistance.PaiementJPAEntity;
import org.springframework.stereotype.Component;

@Component
public class PaiementMapper {
    /** Convertit une entité JPA en DTO de réponse.*/
    public PaiementResponseDTO toDTO(PaiementJPAEntity entity) {
        PaiementResponseDTO dto = new PaiementResponseDTO();
        dto.setId(entity.getId());
        dto.setMontant(entity.getMontant());
        dto.setMode(entity.getMode());
        dto.setDatePaiement(entity.getDatePaiement());
        dto.setJustificatif(entity.getJustificatif());

        if (entity.getInscription() != null) {
            dto.setInscriptionId(entity.getInscription().getId());
        }

        return dto;
    }

    /**
     * Convertit un DTO de requête en entité JPA.
     */
    public PaiementJPAEntity toEntity(PaiementRequestDTO dto) {
        return PaiementJPAEntity.builder()
                .id(dto.getId())
                .montant(dto.getMontant())
                .mode(dto.getMode())
                .datePaiement(dto.getDatePaiement())
                .justificatif(dto.getJustificatif())
                .build();
    }

    /**
     * Associe une inscription au paiement.
     */
    public void attachInscription(PaiementJPAEntity paiement, InscriptionGradeJPAEntity inscription) {
        paiement.setInscription(inscription);
    }
}
