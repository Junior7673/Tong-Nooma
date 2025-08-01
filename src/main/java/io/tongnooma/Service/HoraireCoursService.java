package io.tongnooma.Service;

import io.tongnooma.Dto.HoraireCoursRequestDTO;
import io.tongnooma.Dto.HoraireCoursResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.HoraireCoursMapper;
import io.tongnooma.Persistance.HoraireCoursJPAEntity;
import io.tongnooma.Repository.HoraireCoursJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HoraireCoursService implements HoraireCoursServiceInterface{

    private final HoraireCoursJPARepository repository;
    private final HoraireCoursMapper mapper;

    @Override
    public HoraireCoursResponseDTO create(HoraireCoursRequestDTO dto) {
        HoraireCoursJPAEntity entity = mapper.toEntity(dto);
        HoraireCoursJPAEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public HoraireCoursResponseDTO update(Long id, HoraireCoursRequestDTO dto) {
        HoraireCoursJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Horaire introuvable avec l'ID : " + id));

        HoraireCoursJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);

        HoraireCoursJPAEntity saved = repository.save(updated);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        HoraireCoursJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Horaire introuvable avec l'ID : " + id));
        repository.delete(existing);

    }

    @Override
    public HoraireCoursResponseDTO getById(Long id) {
        HoraireCoursJPAEntity entity = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Horaire introuvable avec l'ID : " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<HoraireCoursResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
