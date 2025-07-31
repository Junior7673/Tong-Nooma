package io.tongnooma.Repository;

import io.tongnooma.Persistance.PaiementJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementJPARepository extends JpaRepository<PaiementJPAEntity, Long> {

}
