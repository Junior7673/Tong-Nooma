package io.tongnooma.Dto;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
public class HoraireCoursResponseDTO {
    private Long id;

    private DayOfWeek jour;

    private LocalTime heureDebut;

    private LocalTime heureFin;

    private String lieu;
}
