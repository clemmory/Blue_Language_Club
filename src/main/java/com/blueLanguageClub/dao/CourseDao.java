package com.blueLanguageClub.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueLanguageClub.entities.Course;
import java.util.List;
import com.blueLanguageClub.entities.LANGUAGE;


@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

    List<Course> findCoursesByLanguage(LANGUAGE language);

}
