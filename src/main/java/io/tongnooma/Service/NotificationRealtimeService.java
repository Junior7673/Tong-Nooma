package io.tongnooma.Service;

import io.tongnooma.Dto.NotificationDTO;
import io.tongnooma.Dto.NotificationRequestDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.NotificationMapper;
import io.tongnooma.Persistance.NotificationJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.NotificationJPARepository;
import io.tongnooma.Repository.UtilisateurJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationRealtimeService implements NotificationRealtimeServiceInterface {

    private final NotificationJPARepository repo;
    private final UtilisateurJPARepository utilisateurRepo;
    private final NotificationMapper mapper;
    private final SimpMessagingTemplate messagingTemplate;

    /**Crée une notification à partir d'un RequestDTO, la sauvegarde,
     * convertit en NotificationDTO et la pousse via STOMP sur /topic/notifications/{userId}
     */
    /**
    public NotificationDTO createAndPush(NotificationRequestDTO request) {
        if (request == null || request.getDestinataireId() == null) {
            throw new IllegalArgumentException("Destinataire requis.");
        }
        UtilisateurJPAEntity user = utilisateurRepo.findById(request.getDestinataireId())
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", request.getDestinataireId()));

        // Convertir request -> entity (mapper gère la validation du type etc.)
        NotificationJPAEntity entity = mapper.toEntityFromRequest(request);

        // Définitions sûres / overrides
        entity.setDestinataire(user);
        // si le DTO dateEnvoi est null, on met maintenant
        if (entity.getDateEnvoi() == null) {
            entity.setDateEnvoi(new Date());
        }
        entity.setLu(false); // forcer false pour sécurité

        NotificationJPAEntity saved = repo.save(entity);

        NotificationDTO dto = mapper.toDTO(saved);

        // push via STOMP sur un topic spécifique à l'utilisateur
        String destination = "/topic/notifications/" + user.getId();
        messagingTemplate.convertAndSend(destination, dto);

        return dto;
    }

    @Override
    public NotificationDTO markAsRead(Long notificationId) {
        NotificationJPAEntity n = repo.findById(notificationId)
                .orElseThrow(() -> new RessourceIntrouvableException("Notification", notificationId));
        n.setLu(true);
        NotificationJPAEntity saved = repo.save(n);

        NotificationDTO dto = mapper.toDTO(saved);
        // push update (optionnel) pour le destinataire
        messagingTemplate.convertAndSend("/topic/notifications/" + saved.getDestinataire().getId(), dto);
        return dto;
    }

    @Override
    public NotificationDTO getById(Long id) {
        return repo.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new RessourceIntrouvableException("Notification", id));
    }
    public List<NotificationDTO> getByUser(Long utilisateurId) {
        UtilisateurJPAEntity user = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", utilisateurId));

        return repo.findByDestinataire(user).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getUnreadByUser(Long utilisateurId) {
        UtilisateurJPAEntity user = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", utilisateurId));

        return repo.findByDestinataireAndLuFalse(user).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }*/

    public NotificationDTO createAndPush(NotificationRequestDTO request) {
        if (request == null || request.getDestinataireId() == null) {
            throw new IllegalArgumentException("Destinataire requis.");
        }

        Long currentUserId = getCurrentUserId();
        UtilisateurJPAEntity expediteur = utilisateurRepo.findById(currentUserId)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", currentUserId));

        UtilisateurJPAEntity destinataire = utilisateurRepo.findById(request.getDestinataireId())
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", request.getDestinataireId()));

        // Mapper request → entity
        NotificationJPAEntity entity = mapper.toEntityFromRequest(request);
        entity.setDestinataire(destinataire);
        entity.setLu(false);
        if (entity.getDateEnvoi() == null) {
            entity.setDateEnvoi(new Date());
        }

        NotificationJPAEntity saved = repo.save(entity);
        NotificationDTO dto = mapper.toDTO(saved);
        // Envoi via STOMP au destinataire uniquement
        messagingTemplate.convertAndSend("/topic/notifications/" + destinataire.getId(), dto);

        return dto;
    }

    @Override
    public NotificationDTO markAsRead(Long notificationId) {
        Long currentUserId = getCurrentUserId();

        NotificationJPAEntity notif = repo.findById(notificationId)
                .orElseThrow(() -> new RessourceIntrouvableException("Notification", notificationId));

        // Vérifier que c’est bien le destinataire
        if (!notif.getDestinataire().getId().equals(currentUserId)) {
            throw new SecurityException("Vous ne pouvez pas modifier cette notification.");
        }

        notif.setLu(true);
        NotificationJPAEntity saved = repo.save(notif);
        NotificationDTO dto = mapper.toDTO(saved);

        // Push de la mise à jour
        messagingTemplate.convertAndSend("/topic/notifications/" + saved.getDestinataire().getId(), dto);

        return dto;
    }
    @Override
    public NotificationDTO getById(Long id) {
        Long currentUserId = getCurrentUserId();

        NotificationDTO dto = repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RessourceIntrouvableException("Notification", id));

        // Vérification de propriété
        if (!dto.getDestinataireId().equals(currentUserId)) {
            throw new SecurityException("Accès interdit à cette notification.");
        }

        return dto;
    }
    public List<NotificationDTO> getByUser(Long utilisateurId) {
        UtilisateurJPAEntity user = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", utilisateurId));

        return repo.findByDestinataire(user).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getUnreadByUser(Long utilisateurId) {
        UtilisateurJPAEntity user = utilisateurRepo.findById(utilisateurId)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", utilisateurId));

        return repo.findByDestinataireAndLuFalse(user).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
    /**
     * Récupère l’ID utilisateur depuis le JWT en session.
     */
    private Long getCurrentUserId() {
        var context = SecurityContextHolder.getContext();
        var auth = context != null ? context.getAuthentication() : null;
        if (auth == null) {
            throw new org.springframework.security.authentication.AuthenticationCredentialsNotFoundException("Non authentifié");
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof CustomUserDetails cud) {
            return cud.getId();
        }

        if (principal instanceof org.springframework.security.core.userdetails.User u) {
            // username = email chez toi
            return utilisateurRepo.findByEmail(u.getUsername())
                    .map(UtilisateurJPAEntity::getId)
                    .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("Utilisateur introuvable"));
        }

        if (principal instanceof String username) {
            // "anonymousUser" → 401 intentionnelle
            if ("anonymousUser".equalsIgnoreCase(username)) {
                throw new org.springframework.security.access.AccessDeniedException("Authentification requise");
            }
            // Sinon, essaye de résoudre par email
            return utilisateurRepo.findByEmail(username)
                    .map(UtilisateurJPAEntity::getId)
                    .orElseThrow(() -> new org.springframework.security.core.userdetails.UsernameNotFoundException("Utilisateur introuvable"));
        }

        throw new org.springframework.security.authentication.AuthenticationCredentialsNotFoundException("Principal invalide");
    }


}
