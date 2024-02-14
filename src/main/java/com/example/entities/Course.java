package com.example.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="courses")

public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private LocalDate date;
    private LocalTime time;
    private MODE mode;
    private String place;
    private LANGUAGE language;
    private LEVEL level;
    private int max_users;

    //Ajouter la relation MANY to MANY avec users
    // Il faudra ajouter les validations

}
