package com.blueLanguageClub.services;

import java.util.List;

import com.blueLanguageClub.entities.Student;

public interface StudentService {

    //Créer un participant
    public Student saveStudent(Student student);

    //Afficher un participant grace a son id
    public Student findByStudent(int idStudent);

    //Supprimer un participant grâce a son id 
    public void deleteById(int idStudent);

    //Supprimer un participant avec son globalId
    public void deleteStudentByGlobalId(String globalId);

    //Afficher tous les participants
    public List<Student> findAllStudents();

    //Afficher un étudiant grâce à son globalId
    public Student findStudentByGlobalId(String globalId);

    //Récupérer tous les étudiants d'un cours
    public List<Student> findStudentsByCoursesId(int courseId);

    //Vérifier si un étudiant existe grâce à son global Id
    public boolean existsByGlobalId (String globalId);


}