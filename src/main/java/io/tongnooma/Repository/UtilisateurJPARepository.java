package io.tongnooma.Repository;

import io.tongnooma.Persistance.UtilisateurJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurJPARepository extends JpaRepository<UtilisateurJPAEntity, Long> {
    Optional<UtilisateurJPAEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
