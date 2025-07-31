package io.tongnooma.Dto;

import io.tongnooma.Persistance.UtilisateurJPAEntity;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UtilisateurRequestDTO {

    /** DTO utilisé pour créer ou modifier un utilisateur via l'API.*/
    private Long id;

    private String nom;

    private String prenom;

    private String email;

    private String telephone;

    private String password;

    private UtilisateurJPAEntity.RoleEnum role;

    private LocalDateTime dateInscrip;
}
