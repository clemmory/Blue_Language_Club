package com.blueLanguageClub.services;


import java.util.List;

import org.springframework.data.domain.Sort;

import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.LANGUAGE;

public interface CourseService {
   
   
    // Créer un cours
    public Course saveCourse (Course course);

    //Supprimer un cours
    public void deleteCourse(Course course);

    //Afficher liste des cours
    public List<Course> findAllCourses();

    //Afficher un cours par Id
    public Course findByIdCourse(int id);

    //Supprimer un cours grâce a son id 
    public void deleteById(int id);
    
    //Récupérer tous les courses par langue
    public List<Course> findCoursesByLanguage(LANGUAGE language);

    ////Récupérer tous les courses classifiés
    public List<Course> findAllCoursesSorted(Sort sort);

    public boolean isCourseInFuture(Course course);

    public int calculateNumStudents(Course course);

    public boolean existsById(Integer courseId);
    
}
