package com.blueLanguageClub.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueLanguageClub.entities.Student;
import com.blueLanguageClub.services.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    //POST - Enregistrer un étudiant {/api/students}
    @PostMapping("/students")
    @Transactional
    public ResponseEntity<Map<String, Object>> saveStudent(@Valid @RequestBody Student student, BindingResult validationResults) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Vérifier si l'objet student comporte des erreurs
        if (validationResults.hasErrors()) {
            List<String> errorsList = new ArrayList<>();

            List<ObjectError> objectErrors = validationResults.getAllErrors();
            objectErrors.forEach(objectError -> errorsList.add(objectError.getDefaultMessage()));

            responseAsMap.put("error", errorsList);
            responseAsMap.put("Incorrect Student", student);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        try {
            long globalId = student.generateGlobalId();
            student.setGlobalId(globalId);
            Student savedStudent = studentService.saveStudent(student);
            String successMessage = "The student has been added successfully.";
            responseAsMap.put("Success message", successMessage);
            responseAsMap.put("Saved student", savedStudent);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String error = "Failed to add new student " + e.getMostSpecificCause();
            responseAsMap.put("error", error);
            responseAsMap.put("student not saved", student);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    //GET Afficher tous les étudiants {/api/students}
    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents(){

        List<Student> students = studentService.findAllStudents();

        return new ResponseEntity<>(students,HttpStatus.OK);
    }

}
