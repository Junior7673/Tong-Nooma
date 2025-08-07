package io.tongnooma.Dto;

import io.tongnooma.Persistance.MediaJPAEntity;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class MediaResponseDTO {

    /** DTO utilisé pour exposer les informations sur un média.*/
    private Long id;

    private MediaJPAEntity.MediaTypeEnum type;

    private String url;

    private String description;

    private Date dateAjout;
}
