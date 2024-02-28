package com.blueLanguageClub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private String globalId;
    private String firstName;
    private String surname;
    private String email;

}
