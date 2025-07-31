package io.tongnooma.Repository;

import io.tongnooma.Persistance.EnseignantJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnseignantJPARepository extends JpaRepository<EnseignantJPAEntity, Long> {
}
