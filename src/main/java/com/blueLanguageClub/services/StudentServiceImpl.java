package com.blueLanguageClub.services;

import java.util.List;
import java.util.Optional;

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
    public List<Student> findAllStudents() {
        return studentDao.findAll();
    }

    @Override
    public Student findStudentByGlobalId(long globalId) {
        return studentDao.findStudentByGlobalId(globalId);
    }

    @Override
    public void deleteStudentByGlobalId(long globalId) {
        studentDao.deleteStudentByGlobalId(globalId);
    }

}
