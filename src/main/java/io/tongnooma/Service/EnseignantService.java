package io.tongnooma.Service;

import io.tongnooma.Dto.EnseignantRequestDTO;
import io.tongnooma.Dto.EnseignantResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.EnseignantMapper;
import io.tongnooma.Persistance.EnseignantJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.EnseignantJPARepository;
import io.tongnooma.Repository.UtilisateurJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantService implements EnseignantServiceInterface{

    private final EnseignantJPARepository repository;
    private final EnseignantMapper mapper;
    private final UtilisateurJPARepository utilisateurJPARepository;

   @Override
   public EnseignantResponseDTO create(EnseignantRequestDTO dto) {
       // Recherche du compte utilisateur correspondant
       UtilisateurJPAEntity utilisateur = utilisateurJPARepository.findById(dto.getUtilisateurId())
               .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", dto.getUtilisateurId()));

       // Conversion du DTO en entité (sans utilisateur)
       EnseignantJPAEntity entity = mapper.toEntity(dto);

       // Lien explicite avec l’utilisateur existant
       mapper.attachUtilisateur(entity, utilisateur);

       // Sauvegarde dans la base
       EnseignantJPAEntity saved = repository.save(entity);

       // Retour du DTO
       return mapper.toDTO(saved);
   }

    @Override
    public EnseignantResponseDTO update(Long id, EnseignantRequestDTO dto) {
        // Recherche de l’enseignant existant
        EnseignantJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Enseignant introuvable avec l'ID : " + id));

        // Mise à jour champ par champ
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setGrade(dto.getGrade());
        existing.setBiographie(dto.getBiographie());
        existing.setPhotoUrl(dto.getPhotoUrl());

        // Optionnel : mise à jour du lien utilisateur si DTO contient un autre ID
        if (dto.getUtilisateurId() != null &&
                !dto.getUtilisateurId().equals(existing.getUtilisateur().getId())) {

            UtilisateurJPAEntity utilisateur = utilisateurJPARepository.findById(dto.getUtilisateurId())
                    .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", dto.getUtilisateurId()));

            existing.setUtilisateur(utilisateur);
        }

        // Sauvegarde et retour DTO
        EnseignantJPAEntity saved = repository.save(existing);
        return mapper.toDTO(saved);
    }


    @Override
    public void delete(Long id) {
        EnseignantJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Enseignant introuvable avec l'ID : " + id));
        repository.delete(existing);
    }

    @Override
    public EnseignantResponseDTO getById(Long id) {
        EnseignantJPAEntity entity = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Enseignant introuvable avec l'ID : " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<EnseignantResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
