package io.tongnooma.Service;

import io.tongnooma.Dto.NotificationDTO;
import io.tongnooma.Dto.NotificationRequestDTO;

import java.util.List;

public interface NotificationRealtimeServiceInterface {

    NotificationDTO createAndPush(NotificationRequestDTO request);
    NotificationDTO markAsRead(Long notificationId);
    NotificationDTO getById(Long id);
    List<NotificationDTO> getByUser(Long utilisateurId);
    List<NotificationDTO> getUnreadByUser(Long utilisateurId);
}
