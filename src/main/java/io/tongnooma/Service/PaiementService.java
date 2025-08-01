package io.tongnooma.Service;

import io.tongnooma.Dto.PaiementRequestDTO;
import io.tongnooma.Dto.PaiementResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.PaiementMapper;
import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import io.tongnooma.Persistance.PaiementJPAEntity;
import io.tongnooma.Repository.InscriptionGradeJPARepository;
import io.tongnooma.Repository.PaiementJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaiementService implements PaiementServiceInterface{

    private final PaiementJPARepository repository;
    private final PaiementMapper mapper;
    private final InscriptionGradeJPARepository inscriptionRepository;

    @Override
    public PaiementResponseDTO create(PaiementRequestDTO dto) {
        // Vérifier que l'inscription liée existe
        InscriptionGradeJPAEntity inscription = inscriptionRepository.findById(dto.getInscriptionId())
                .orElseThrow(() -> new RessourceIntrouvableException("Inscription introuvable avec l'ID : " + dto.getInscriptionId()));

        // Mapper et assigner l'inscription
        PaiementJPAEntity entity = mapper.toEntity(dto);
        entity.setInscription(inscription);

        PaiementJPAEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public PaiementResponseDTO update(Long id, PaiementRequestDTO dto) {
        PaiementJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Paiement introuvable avec l'ID : " + id));

        InscriptionGradeJPAEntity inscription = inscriptionRepository.findById(dto.getInscriptionId())
                .orElseThrow(() -> new RessourceIntrouvableException("Inscription introuvable avec l'ID : " + dto.getInscriptionId()));

        PaiementJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        updated.setInscription(inscription);

        PaiementJPAEntity saved = repository.save(updated);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        PaiementJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Paiement introuvable avec l'ID : " + id));
        repository.delete(existing);
    }

    @Override
    public PaiementResponseDTO getById(Long id) {
        PaiementJPAEntity entity = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Paiement introuvable avec l'ID : " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<PaiementResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
