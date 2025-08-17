package io.tongnooma.Repository;

import io.tongnooma.Persistance.NotificationJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJPARepository extends JpaRepository<NotificationJPAEntity, Long> {
    // Récupérer les notifications d’un utilisateur
    List<NotificationJPAEntity> findByDestinataire(UtilisateurJPAEntity utilisateur);

    // Récupérer les notifications non lues d’un utilisateur
    List<NotificationJPAEntity> findByDestinataireAndLuFalse(UtilisateurJPAEntity utilisateur);
}
