package com.blueLanguageClub.entities;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "students")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please enter your firstname.")
    @Pattern(regexp = "^[A-Za-z\s-]+$")
    private String firstName;

    @NotBlank(message = "Please enter your surname.")
    @Pattern(regexp = "^[A-Za-z\s-]+$")
    private String surname;

    @Column(name = "global_id")
    private long globalId;
    
    @Pattern(regexp = "[a-zA-Z0-9._%+-]+@blueclub.com")
    @Email
    @NotBlank(message= "Please verify you have entered a correct email address.")
    private String email;

    @Enumerated
    @NotNull(message = "Please indicate the language you want to improve.")
    private LANGUAGE language;

    @Column(name = "initial_level")
    @Enumerated
    @NotNull(message = "Please indicate your current level.")
    private LEVEL initialLevel;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }, mappedBy = "students")
    @JsonIgnore
    private List<Course> courses;
    //Changer pour SET (aussi dans datos de muestras)


}
