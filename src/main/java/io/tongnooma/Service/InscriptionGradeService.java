package io.tongnooma.Service;

import io.tongnooma.Dto.InscriptionGradeRequestDTO;
import io.tongnooma.Dto.InscriptionGradeResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.InscriptionGradeMapper;
import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import io.tongnooma.Repository.InscriptionGradeJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscriptionGradeService implements InscriptionGradeServiceInterface {

    private final InscriptionGradeJPARepository repository;
    private final InscriptionGradeMapper mapper;

    @Override
    public InscriptionGradeResponseDTO create(InscriptionGradeRequestDTO dto) {
        InscriptionGradeJPAEntity entity = mapper.toEntity(dto);
        InscriptionGradeJPAEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public InscriptionGradeResponseDTO update(Long id, InscriptionGradeRequestDTO dto) {
        InscriptionGradeJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Inscription introuvable avec l'ID : " + id));
        InscriptionGradeJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        InscriptionGradeJPAEntity saved = repository.save(updated);
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