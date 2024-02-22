package com.blueLanguageClub.services;

import java.util.List;
import java.util.Optional;

import com.blueLanguageClub.entities.Student;

public interface StudentService {

    //Créer un participant
    public Student saveStudent(Student student);

    //Afficher un participant grace a son id
    public Student findByStudent(int idStudent);

    //Supprimer un participant
    public void deleteStudent(Student student);

    //Afficher tous les participants
    public List<Student> findAllStudents();

    //Afficher un étudiant grâce à son globalId
    public Optional findStudentByGlobalId(long global_id);

}
