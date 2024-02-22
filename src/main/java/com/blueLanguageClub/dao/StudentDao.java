package com.blueLanguageClub.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueLanguageClub.entities.Student;
import java.util.Optional;


@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {

Optional<Student> findStudentByGlobalId(long globalId);


}
