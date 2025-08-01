package io.tongnooma.Service;

import io.tongnooma.Dto.MediaRequestDTO;
import io.tongnooma.Dto.MediaResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.MediaMapper;
import io.tongnooma.Persistance.MediaJPAEntity;
import io.tongnooma.Repository.MediaJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService implements MediaServiceInterface{

    private final MediaJPARepository repository;
    private final MediaMapper mapper;

    @Override
    public MediaResponseDTO create(MediaRequestDTO dto) {
        MediaJPAEntity entity = mapper.toEntity(dto);
        MediaJPAEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public MediaResponseDTO update(Long id, MediaRequestDTO dto) {
        MediaJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Media introuvable avec l'ID : " + id));
        MediaJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        MediaJPAEntity saved = repository.save(updated);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(Long id) {
        MediaJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Media introuvable avec l'ID : " + id));
        repository.delete(existing);
    }

    @Override
    public MediaResponseDTO getById(Long id) {
        MediaJPAEntity entity = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Media introuvable avec l'ID : " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<MediaResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
