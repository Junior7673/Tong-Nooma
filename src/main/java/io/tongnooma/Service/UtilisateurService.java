package io.tongnooma.Service;

import io.tongnooma.Dto.UtilisateurRequestDTO;
import io.tongnooma.Dto.UtilisateurResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.UtilisateurMapper;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.UtilisateurJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService implements UtilisateurServiceInterface {

    private final UtilisateurJPARepository repository;
    private final UtilisateurMapper mapper;

    @Override
    public UtilisateurResponseDTO create(UtilisateurRequestDTO dto) {
        UtilisateurJPAEntity entity = mapper.toEntity(dto);
        UtilisateurJPAEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public UtilisateurResponseDTO update(Long id, UtilisateurRequestDTO dto) {
        UtilisateurJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur introuvable avec l'ID : " + id));
        UtilisateurJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        UtilisateurJPAEntity saved = repository.save(updated);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        UtilisateurJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur introuvable avec l'ID : " + id));
        repository.delete(existing);
    }

    @Override
    public UtilisateurResponseDTO getById(Long id) {
        UtilisateurJPAEntity entity = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur introuvable avec l'ID : " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<UtilisateurResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
