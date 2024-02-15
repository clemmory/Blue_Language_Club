package com.example.services;

import java.util.List;

import com.example.entities.Course;

public interface CourseService {

    // Créer un cours
    public Course saveCourse (Course course);
    //Supprimer un cours
    public void deleteCourse(Course course);
    //Afficher liste des cours
    public List<Course> findAllCourses();
    //Afficher un cours par Id
    public Course findByIdCourse(int id);
    
    

}
