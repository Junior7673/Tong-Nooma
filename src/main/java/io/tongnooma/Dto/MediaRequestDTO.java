package io.tongnooma.Dto;

import io.tongnooma.Persistance.MediaJPAEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MediaRequestDTO {
    /** DTO pour créer ou modifier un média (image, vidéo...).*/
    private Long id;

    private MediaJPAEntity.MediaTypeEnum type; // PHOTO ou VIDEO

    private String url;

    private String description;

    private Date dateAjout;
}
