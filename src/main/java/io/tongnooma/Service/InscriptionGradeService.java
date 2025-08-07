package io.tongnooma.Service;

import io.tongnooma.Dto.EnseignantRequestDTO;
import io.tongnooma.Dto.EnseignantResponseDTO;
import io.tongnooma.Dto.InscriptionGradeRequestDTO;
import io.tongnooma.Dto.InscriptionGradeResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.InscriptionGradeMapper;
import io.tongnooma.Persistance.EnseignantJPAEntity;
import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.InscriptionGradeJPARepository;
import io.tongnooma.Repository.UtilisateurJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscriptionGradeService implements InscriptionGradeServiceInterface {

    private final InscriptionGradeJPARepository repository;
    private final InscriptionGradeMapper mapper;
    private final UtilisateurJPARepository utilisateurRepo;


    @Override
    public InscriptionGradeResponseDTO create(InscriptionGradeRequestDTO dto) {
        // Recherche du compte utilisateur correspondant
        UtilisateurJPAEntity utilisateur = utilisateurRepo.findById(dto.getUtilisateurId())
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", dto.getUtilisateurId()));

        // Conversion du DTO en entité (sans utilisateur)
        InscriptionGradeJPAEntity entity = mapper.toEntity(dto);

        // Lien explicite avec l’utilisateur existant
        mapper.attachUtilisateur(entity, utilisateur);

        // Sauvegarde dans la base
        InscriptionGradeJPAEntity saved = repository.save(entity);

        // Retour du DTO
        return mapper.toDTO(saved);
    }


    /**@Override
    public InscriptionGradeResponseDTO update(Long id, InscriptionGradeRequestDTO dto) {
        InscriptionGradeJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Inscription introuvable avec l'ID : " + id));
        InscriptionGradeJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        InscriptionGradeJPAEntity saved = repository.save(updated);
        return mapper.toDTO(saved);
    }*/
    @Override
    public InscriptionGradeResponseDTO update(Long id, InscriptionGradeRequestDTO dto) {
        // Recherche de l’inscription existant
        InscriptionGradeJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Inscription introuvable avec l'ID : " + id));

        // Mise à jour champ par champ
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setAge(dto.getAge());
        existing.setGradeActuel(dto.getGradeActuel());
        existing.setNomClub(dto.getNomClub());
        existing.setDateSoumission(dto.getDateSoumission());
        existing.setPhoto(dto.getPhoto());
        existing.setStatut(dto.getStatut());


        // Optionnel : mise à jour du lien utilisateur si DTO contient un autre ID
        if (dto.getUtilisateurId() != null) {
            Long existingUserId = existing.getUtilisateur() != null ? existing.getUtilisateur().getId() : null;

            if (!dto.getUtilisateurId().equals(existingUserId)) {
                UtilisateurJPAEntity utilisateur = utilisateurRepo.findById(dto.getUtilisateurId())
                        .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", dto.getUtilisateurId()));
                existing.setUtilisateur(utilisateur);
            }
        }

        // Sauvegarde et retour DTO
        InscriptionGradeJPAEntity saved = repository.save(existing);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        InscriptionGradeJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Inscription introuvable avec l'ID : " + id));
        repository.delete(existing);
    }

    @Override
    public InscriptionGradeResponseDTO getById(Long id) {
        InscriptionGradeJPAEntity entity = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Inscription introuvable avec l'ID : " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<InscriptionGradeResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InscriptionGradeResponseDTO> getByStatut(String statut) {
        try {
            InscriptionGradeJPAEntity.StatutEnum statutEnum =
                    InscriptionGradeJPAEntity.StatutEnum.valueOf(statut.toUpperCase());
            return repository.findByStatut(statutEnum)
                    .stream()
                    .map(mapper::toDTO)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new RessourceIntrouvableException("Statut invalide : " + statut);
        }
    }
}