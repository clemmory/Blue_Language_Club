package com.blueLanguageClub.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blueLanguageClub.dao.CourseDao;
import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.LANGUAGE;
import com.blueLanguageClub.entities.Student;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

 public class CourseServiceImpl implements CourseService {
    
    private final CourseDao courseDao;

    @Override
    public Course saveCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    public void deleteCourse(Course course) {
       courseDao.delete(course);
    }

    @Override
    public List<Course> findAllCourses() {
      return courseDao.findAll();
    }

    @Override
    public Course findByIdCourse(int id) {
        return courseDao.findById(id).get();
    }

    @Override
    public void deleteById(int id) {
      courseDao.deleteById(id);
    }

    @Override
    public boolean existsById(Integer courseId) {
      return courseDao.existsById(courseId);
    }
    


    @Override
    public List<Course> findCoursesByLanguage(LANGUAGE language) {
      return courseDao.findCoursesByLanguage(language);
    }

    @Override
    public List<Course> findAllCoursesSorted(Sort sort) {
      return courseDao.findAll(sort);
    }

    @Override
    public boolean isCourseInFuture(Course course) {
      return (course.getDate().isEqual(LocalDate.now()) && course.getTime().isAfter(LocalTime.now()) || course.getDate().isAfter(LocalDate.now())) ? true : false;   
    }

    @Override
    public int calculateNumStudents(Course course) {
     
      int max_students = course.getMax_students();
      int registeredStudents = 0;
      Set<Student> listStudents = course.getStudents();
          if(listStudents != null) {
              registeredStudents = listStudents.size();
            }
      
      //int result = max_students-registeredStudents;

     
      return registeredStudents;

    }
  
 }