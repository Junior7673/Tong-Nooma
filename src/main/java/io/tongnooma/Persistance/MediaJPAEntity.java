package io.tongnooma.Persistance;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "media")
public class MediaJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MediaTypeEnum type; // PHOTO ou VIDEO

    private String url;
    private String description;
    private LocalDate dateAjout;

    public enum MediaTypeEnum{
        PHOTO,
        VIDEO
    }
}
