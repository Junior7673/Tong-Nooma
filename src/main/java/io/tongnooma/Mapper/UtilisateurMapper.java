package io.tongnooma.Mapper;

import io.tongnooma.Dto.UtilisateurRequestDTO;
import io.tongnooma.Dto.UtilisateurResponseDTO;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurMapper {
    /**
      Convertit une entité JPA en DTO de réponse.
     */
    public UtilisateurResponseDTO toDTO(UtilisateurJPAEntity entity) {
        UtilisateurResponseDTO dto = new UtilisateurResponseDTO();
        dto.setId(entity.getId());
        dto.setNom(entity.getNom());
        dto.setPrenom(entity.getPrenom());
        dto.setEmail(entity.getEmail());
        dto.setTelephone(entity.getTelephone());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setDateInscrip(entity.getDateInscrip());
        return dto;
    }
    /**
      Convertit un DTO de requête en entité JPA.
     */
    public UtilisateurJPAEntity toEntity(UtilisateurRequestDTO dto) {
        return UtilisateurJPAEntity.builder()
                .id(dto.getId())
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .email(dto.getEmail())
                .telephone(dto.getTelephone())
                .password(dto.getPassword())
                .role(dto.getRole())
                .dateInscrip(dto.getDateInscrip())
                .build();
    }
}
