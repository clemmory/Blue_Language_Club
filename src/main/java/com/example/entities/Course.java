package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please indicate the chosen course")
    private String title;

    @NotNull
    @FutureOrPresent
    //Commenrt est-que on peut changer le format international
    @DateTimeFormat(pattern = "dd-MM-yyyy")//En el pdf DD-MM-YYYY
    private LocalDate date;

    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;
    
    @Enumerated
    @NotNull(message = "Please indicate the course type")
    private MODE mode;
    
    // On doit chercher les patterns pour les numeros et signe de punctuation
    //@Pattern(regexp = "^[A-Za-z0-9 -]+$")
    @NotNull(message = "Please indicate the place.")
    private String place;
    
    @Enumerated
    @NotNull(message = "Please indicate the language you want to improve.")
    private LANGUAGE language;
    
    @Enumerated
    @NotNull(message = "Please indicate your current level.")
    private LEVEL level;
    
    @DecimalMax(value = "8", inclusive = true)
    @NotNull(message = "")
    private int max_users;

    // Ajouter la relation MANY to MANY avec users
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })

    @JoinTable(name = "user_courses", joinColumns = { 
        @JoinColumn(name = "course_id") }, inverseJoinColumns = {
            @JoinColumn(name = "user_id") })
    private List<User> users;


    // Il faudra ajouter les validations

}
