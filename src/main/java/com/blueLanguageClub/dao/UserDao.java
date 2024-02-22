package com.blueLanguageClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueLanguageClub.entities.Student;

@Repository
public interface UserDao extends JpaRepository<Student, Integer> {

}
