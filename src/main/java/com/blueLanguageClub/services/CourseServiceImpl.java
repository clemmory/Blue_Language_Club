package com.blueLanguageClub.services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.blueLanguageClub.dao.CourseDao;
import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.LANGUAGE;

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
    public List<Course> findCoursesByLanguage(LANGUAGE language) {
      return courseDao.findCoursesByLanguage(language);
    }
    

 }