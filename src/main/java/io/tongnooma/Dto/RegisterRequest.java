package io.tongnooma.Dto;

import io.tongnooma.Persistance.UtilisateurJPAEntity;
import lombok.Data;

import java.util.Date;

@Data
public class RegisterRequest {

    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String password;
    private UtilisateurJPAEntity.RoleEnum role;
    private Date dateInscrip;

}
