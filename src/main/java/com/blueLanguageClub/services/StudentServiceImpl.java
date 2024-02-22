package com.blueLanguageClub.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blueLanguageClub.dao.StudentDao;
import com.blueLanguageClub.entities.Student;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    
    private final StudentDao studentDao;
    
    @Override
    public Student saveStudent(Student student) {
        return studentDao.save(student);
     
    }

    @Override
    public Student findByStudent(int idStudent) {
        return studentDao.findById(idStudent).get();

    }

    @Override
    public void deleteStudent(Student student) {
        studentDao.delete(student);

    }

    @Override
    public List<Student> findAllStudents() {
        return studentDao.findAll();
    }

}
