package io.tongnooma.Persistance;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notification")
public class NotificationJPAEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;              // Sujet de la notification
    private String message;            // Contenu de l'alerte

    @Enumerated(EnumType.STRING)
    private TypeNotification type;     // INFO, ALERTE, URGENT...

    private boolean lu;                 // True si l'utilisateur l’a déjà consultée
    private Date dateEnvoi;

    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private UtilisateurJPAEntity destinataire; // L'utilisateur concerné

    public enum TypeNotification {
        INFO,
        ALERTE,
        URGENT
    }
}
