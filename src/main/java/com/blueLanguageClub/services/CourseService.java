package com.blueLanguageClub.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.blueLanguageClub.dao.CourseDao;
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
}

    //Trouver des courses disponibles pour dates prochaines
    // public static List<Course> getAvailableCoursesForFutureDates() {
    //     Date currentDate = new Date(); // date actuelle

    //     return CourseDao.findByDateAfter(currentDate);
    // }
    //     @Autowired
    //     private CourseDao courseDao;
    //     List<Course> futureCourses = courseDao.findByDateAfter(currentDate);

    //     // Eliminer les courses qui sont completes
    //     List<Course> availableCourses = new ArrayList<>();
    //     for (Course course : futureCourses) {
    //         if (course.getStudents().size() < course.getMax_students()) {
    //             availableCourses.add(course);
    //         }
    //     }
    
    //     return availableCourses;
    // }








