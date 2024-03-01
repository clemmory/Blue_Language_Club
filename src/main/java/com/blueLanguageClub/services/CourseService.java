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

    //Supprimer un cours grâce à son id
    public void deleteById(int id);

    //Vérifier l'existence d'un cours grâce à son id
    public boolean existsById(Integer courseId);
    
    //Récupérer la liste des cours par langue
    public List<Course> findCoursesByLanguage(LANGUAGE language);

    //Récupérer la liste des cours organisée par date
    public List<Course> findAllCoursesSorted(Sort sort);

    //Vérifier si la date d'un cours est dans le futur
    public boolean isCourseInFuture(Course course);

    //Calculer le nombre d'étudiants par cours
    public int calculateNumStudents(Course course);

    //Vérifier si un cours est complet
    public boolean isComplete(Course course);
    
}
