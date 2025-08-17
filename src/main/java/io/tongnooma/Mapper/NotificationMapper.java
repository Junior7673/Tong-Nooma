package io.tongnooma.Mapper;

import io.tongnooma.Dto.NotificationDTO;
import io.tongnooma.Dto.NotificationRequestDTO;
import io.tongnooma.Persistance.NotificationJPAEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NotificationMapper {

    public NotificationDTO toDTO(NotificationJPAEntity e) {
        if (e == null) return null;
        return NotificationDTO.builder()
                .id(e.getId())
                .titre(e.getTitre())
                .message(e.getMessage())
                .type(e.getType() != null ? e.getType().name() : null)
                .lu(e.isLu())
                .dateEnvoi(e.getDateEnvoi())
                .destinataireId(e.getDestinataire() != null ? e.getDestinataire().getId() : null)
                .build();
    }

    public NotificationJPAEntity toEntityFromRequest(NotificationRequestDTO dto) {
        if (dto == null) return null;
        NotificationJPAEntity.TypeNotification typeEnum = null;
        if (dto.getType() != null) {
            try {
                typeEnum = NotificationJPAEntity.TypeNotification.valueOf(dto.getType().toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Type de notification invalide : " + dto.getType());
            }
        }

        return NotificationJPAEntity.builder()
                .titre(dto.getTitre())
                .message(dto.getMessage())
                .type(typeEnum)
                .lu(false)
                .dateEnvoi(dto.getDateEnvoi() != null ? dto.getDateEnvoi() : new Date())
                .build();
    }

}
