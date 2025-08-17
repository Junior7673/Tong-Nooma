package io.tongnooma.Service;

import io.tongnooma.Dto.UtilisateurRequestDTO;
import io.tongnooma.Dto.UtilisateurResponseDTO;
import io.tongnooma.Exception.RessourceIntrouvableException;
import io.tongnooma.Mapper.UtilisateurMapper;
import io.tongnooma.Persistance.UtilisateurJPAEntity;
import io.tongnooma.Repository.UtilisateurJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UtilisateurService implements UtilisateurServiceInterface {

    private final UtilisateurJPARepository repository;
    private final UtilisateurMapper mapper;
    private final PasswordEncoder passwordEncoder; // 💡 injecté automatiquement
/**
    @Override
    public UtilisateurResponseDTO create(UtilisateurRequestDTO dto) {
        UtilisateurJPAEntity entity = mapper.toEntity(dto);
        UtilisateurJPAEntity saved = repository.save(entity);
        return mapper.toDTO(saved);
    }*/
@Override
public UtilisateurResponseDTO create(UtilisateurRequestDTO dto) {
    UtilisateurJPAEntity entity = mapper.toEntity(dto);

    String password = dto.getPassword();
    if (password != null && !password.isBlank()) {
        entity.setPassword(passwordEncoder.encode(password)); // 🔐 encodage ici
    }

    UtilisateurJPAEntity saved = repository.save(entity);
    return mapper.toDTO(saved);
}


    /**@Override
    public UtilisateurResponseDTO update(Long id, UtilisateurRequestDTO dto) {
        UtilisateurJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur introuvable avec l'ID : " + id));
        UtilisateurJPAEntity updated = mapper.toEntity(dto);
        updated.setId(id);
        UtilisateurJPAEntity saved = repository.save(updated);
        return mapper.toDTO(saved);
    }*/
    @Override
    public UtilisateurResponseDTO update(Long id, UtilisateurRequestDTO dto) {
        UtilisateurJPAEntity existing = repository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Utilisateur introuvable avec l'ID : " + id));

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            String newPassword = dto.getPassword();
            if (!newPassword.startsWith("$2a$") && !newPassword.startsWith("$2b$")) {
                existing.setPassword(passwordEncoder.encode(newPassword)); // 🔐 encodage si mot de passe en clair
            }
        }

        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setEmail(dto.getEmail());
        // ... autres champs
        UtilisateurJPAEntity saved = repository.save(existing);
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
