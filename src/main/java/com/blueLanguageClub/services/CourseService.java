package com.blueLanguageClub.services;

import java.util.List;

import com.blueLanguageClub.entities.Course;

public interface CourseService {

    // Créer un cours
    public Course saveCourse (Course course);
    //Supprimer un cours
    public void deleteCourse(Course course);
    //Afficher liste des cours
    public List<Course> findAllCourses();
    //Afficher un cours par Id
    public Course findByIdCourse(int id);

    public void deleteById(int id);
    
    public boolean existsById(Integer courseId);
    
    

}
