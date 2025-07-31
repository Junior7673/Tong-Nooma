package io.tongnooma.Repository;

import io.tongnooma.Persistance.InscriptionGradeJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface InscriptionGradeJPARepository extends JpaRepository<InscriptionGradeJPAEntity, Long> {
    List<InscriptionGradeJPAEntity> findByStatut(InscriptionGradeJPAEntity.StatutEnum statut);
}
