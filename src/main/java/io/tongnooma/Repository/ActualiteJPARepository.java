package io.tongnooma.Repository;

import io.tongnooma.Persistance.ActualiteJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActualiteJPARepository extends JpaRepository<ActualiteJPAEntity, Long> {
}
