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

    public void deleteById(int id);
    
    public List<Course> findCoursesByLanguage(LANGUAGE language);

    public List<Course> findAllCoursesSorted(Sort sort);

    public boolean isCourseinFuture(Course course);
    
}
