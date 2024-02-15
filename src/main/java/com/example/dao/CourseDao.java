package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.Course;

@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

}
