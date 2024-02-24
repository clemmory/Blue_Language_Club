package com.blueLanguageClub.entities;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;
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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraintvalidation.ValidationTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Please indicate the course title.")
    private String title;

    @NotNull(message = "Please indicate the course date")
    @FutureOrPresent(message = "The indicated date is not valid")
    @DateTimeFormat(pattern = "dd-MM-yyyy")//En el pdf DD-MM-YYYY
    private LocalDate date;

    @NotNull(message = "Please indicate the course time")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    @Enumerated
    @NotNull(message = "Please indicate this field.")
    private MODE mode;

    @NotNull(message = "Please indicate the place.")
    private String place;

    @Enumerated
    @NotNull(message = "Please indicate the language you want to improve.")
    private LANGUAGE language;

    @Enumerated
    @NotNull(message = "Please indicate your current level.")
    private LEVEL level;

    @Max(value = 8)
    @Min(value = 1)
    @NotNull(message = "The maximum number of students for a course is 8.")
    private int max_students;

    // Ajouter la relation MANY to MANY avec users
    @ManyToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "students_courses", joinColumns = { 
        @JoinColumn(name = "course_id") }, inverseJoinColumns = {
            @JoinColumn(name = "student_id") })
    @UniqueElements
    private List<Student> students;
}