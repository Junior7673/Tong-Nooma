package io.tongnooma.Service;

import io.tongnooma.Dto.ActualiteRequestDTO;
import io.tongnooma.Dto.ActualiteResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.ActualiteMapper;
import io.tongnooma.Persistance.ActualiteJPAEntity;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.ActualiteJPARepository;
import io.tongnooma.Repository.UtilisateurJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActualiteService implements ActualiteServiceInterface{

    private final ActualiteJPARepository repository;
    private final UtilisateurJPARepository utilisateurRepo;
    private final ActualiteMapper mapper;

    /** Crée une nouvelle actualité en liant à un auteur (utilisateur). */
    @Override
    public ActualiteResponseDTO create(ActualiteRequestDTO dto) {
        UtilisateurJPAEntity auteur = utilisateurRepo.findById(dto.getAuteurId())
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", dto.getAuteurId()));

        ActualiteJPAEntity entity = mapper.toEntity(dto);
        entity.setAuteur(auteur);

        return mapper.toDTO(repository.save(entity));
    }

    /** Met à jour une actualité existante. */
    @Override
    public ActualiteResponseDTO update(Long id, ActualiteRequestDTO dto) {
        ActualiteJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Actualité introuvable avec l'ID : " + id));

        UtilisateurJPAEntity auteur = utilisateurRepo.findById(dto.getAuteurId())
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur", dto.getAuteurId()));

        ActualiteJPAEntity entity = mapper.toEntity(dto);
        entity.setId(id);
        entity.setAuteur(auteur);

        return mapper.toDTO(repository.save(entity));
    }


    /** Supprime une actualité. */
    @Override
    public void delete(Long id) {
        ActualiteJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Actualité introuvable avec l'ID : " + id));
        repository.delete(existing);
    }

    /** Récupère une actualité par son ID. */
    @Override
    public ActualiteResponseDTO getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RessourceIntrouvableException("Actualité introuvable avec l'ID : " + id));
    }

    /** Liste toutes les actualités. */
    @Override
    public List<ActualiteResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
