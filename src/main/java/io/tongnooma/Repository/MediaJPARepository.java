package io.tongnooma.Repository;

import io.tongnooma.Persistance.MediaJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaJPARepository extends JpaRepository<MediaJPAEntity, Long> {
    List<MediaJPAEntity> findByType(MediaJPAEntity.MediaTypeEnum type);
}
