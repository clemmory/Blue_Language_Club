package com.blueLanguageClub.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueLanguageClub.entities.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

    static List<Course> findByDateAfter(Date currentDate) {
       return null;
    }



}
