package io.tongnooma.Service;

import io.tongnooma.Dto.EnseignantRequestDTO;
import io.tongnooma.Dto.EnseignantResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.EnseignantMapper;
import io.tongnooma.Persistance.EnseignantJPAEntity;
import io.tongnooma.Repository.EnseignantJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantService implements EnseignantServiceInterface{

    private final EnseignantJPARepository repository;
    private final EnseignantMapper mapper;

    @Override
    public EnseignantResponseDTO create(EnseignantRequestDTO dto) {
        EnseignantJPAEntity entity = mapper.toEntity(dto);
        EnseignantJPAEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public EnseignantResponseDTO update(Long id, EnseignantRequestDTO dto) {
        EnseignantJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Enseignant introuvable avec l'ID : " + id));
        EnseignantJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        EnseignantJPAEntity saved = repository.save(updated);
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
