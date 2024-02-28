package com.blueLanguageClub.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.blueLanguageClub.entities.LANGUAGE;
import com.blueLanguageClub.entities.LEVEL;
import com.blueLanguageClub.entities.MODE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseAdminDto {

    private String title;
    private LocalDate date;
    private LocalTime time;
    private MODE mode;
    private String place;
    private LANGUAGE language;
    private LEVEL Level;
    private int registeredStudents;

}
