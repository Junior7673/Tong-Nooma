package io.tongnooma.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDTO {

    private Long id;
    private String titre;
    private String message;
    private String type;           // INFO / ALERTE / URGENT
    private boolean lu;
    private Date dateEnvoi;
    private Long destinataireId;   // uniquement l'id, pas l'objet complet
}
