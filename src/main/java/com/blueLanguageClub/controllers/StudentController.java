package com.blueLanguageClub.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueLanguageClub.dto.CourseStudentDto;
import com.blueLanguageClub.dto.StudentDto;
import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.Student;
import com.blueLanguageClub.services.CourseService;
import com.blueLanguageClub.services.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    // POST - Enregistrer un étudiant {/api/students}
    @PostMapping("/students")
    @Transactional
    public ResponseEntity<Map<String, Object>> saveStudent(@Valid @RequestBody Student student, BindingResult validationResults) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Vérifier si l'objet student comporte des erreurs @Validation
        if (validationResults.hasErrors()) {
            List<String> errorsList = new ArrayList<>();

            List<ObjectError> objectErrors = validationResults.getAllErrors();
            objectErrors.forEach(objectError -> errorsList.add(objectError.getDefaultMessage()));

            responseAsMap.put("error", errorsList);
            responseAsMap.put("Incorrect Student", student);

            // Réponse en cas d'erreur de validation
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        try {
            Student savedStudent = studentService.saveStudent(student);
            String successMessage = "The student has been added successfully.";
            responseAsMap.put("Success message", successMessage);
            responseAsMap.put("Saved student:", savedStudent);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String error = "Failed to add new student " + e.getMostSpecificCause();
            responseAsMap.put("error", error);
            responseAsMap.put("student not saved", student);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // GET Afficher tous les étudiants {/api/students} - OK
    @GetMapping("/students")
    public ResponseEntity<List<Student>> findAllStudents() {
        List<Student> students = studentService.findAllStudents();
        if(students.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    // GET Afficher un étudiant par globalId {/api/students/{globalId}} - OK
    @GetMapping("/students/{globalId}")
    public ResponseEntity<Map<String, Object>> findStudentByGlobalId(
            @PathVariable(value = "globalId", required = true) String globalId) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        try {
            // Vérifier si le globalId existe pour un étudiant donné
            Student student = studentService.findStudentByGlobalId(globalId);
            if (student != null) {
                String successMessage = "Student found with global id : " + globalId;
                responseAsMap.put("Sucess message: ", successMessage);
                responseAsMap.put("Student found: ", student);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {
                String errorMessage = "Student with global id: " + globalId + " not found";
                responseAsMap.put("Error message: ", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            String errorGrave = "Error found for student with globalId : " + globalId;
            responseAsMap.put("Serious error ", errorGrave);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    // PUT Modifier un étudiant en utilisant son global_id {/api/students/{globalId}} - OK
    @PutMapping("/students/{globalId}")
    public ResponseEntity<Map<String, Object>> updateStudentByGlobalId(
            @PathVariable(value = "globalId", required = true) String globalId,
            @Valid @RequestBody Student student, BindingResult validationResults) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        //Vérifier si il y'a des champs invalides
        if (validationResults.hasErrors()) {
            List<String> errorsList = new ArrayList<>();

            List<ObjectError> objectErrors = validationResults.getAllErrors();
            objectErrors.forEach(objectError -> errorsList.add(objectError.getDefaultMessage()));

            responseAsMap.put("error", errorsList);
            responseAsMap.put("Incorrect Student", student);

            // Réponse en cas d'erreur de validation
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        try {
            Student savedStudent = studentService.findStudentByGlobalId(globalId);
            if (savedStudent != null){
                savedStudent.setFirstName(student.getFirstName());
                savedStudent.setSurname(student.getSurname());
                savedStudent.setEmail(student.getEmail());
                savedStudent.setLanguage(student.getLanguage());
                savedStudent.setInitialLevel(student.getInitialLevel());
                String successMessage = "The student with globalId: " + globalId + " has been modified successfully.";
                responseAsMap.put("Sucess Message", successMessage);
                responseAsMap.put("Saved student", savedStudent);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
            } else {
                String errorMessage = "Student with global id: " + globalId + " not found";
                responseAsMap.put("Error message: ", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            }           
        } catch (DataAccessException e) {
            String error = "The student could not be saved : "+ e.getMostSpecificCause();
            responseAsMap.put("Error", error);
            responseAsMap.put("Modified student",student);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    // DELETE Supprimer un étudiant en utilisant son global_id {/api/delete/{globalId}} - NOT WORKING
    @DeleteMapping("/students/{globalId}")
    @Transactional
    public ResponseEntity<Map<String, Object>> deleteStudentByGlobalId(
            @PathVariable(value = "globalId", required = true) String globalId) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;    
        try {
            // Vérifier si le globalId existe pour un étudiant donné
            Student student = studentService.findStudentByGlobalId(globalId);
            if (student != null) {
                // Si l'étudiant existe, le supprimer
                studentService.deleteStudentByGlobalId(globalId);
                String successMessage = "Student with global id : " + globalId + "has been deleted";
                responseAsMap.put("Sucess message: ", successMessage);
                responseAsMap.put("Student deleted: ", student);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
            } else {
                // Sinon, envoyer un message d'erreur
                String errorMessage = "Student with global id: " + globalId + " not found and cannot be deleted";
                responseAsMap.put("Error message: ", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            String errorGrave = "Error found for student con globalId : " + globalId;
            responseAsMap.put("Error grave: ", errorGrave);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);                                                                                                                                                                                                                               // exception
        }

        return responseEntity;
    }


    // GET Récupérer tous les étudiants d'un cours
    @GetMapping("/courses/{courseId}/students")
    public ResponseEntity<Map<String, Object>> getStudentsByCourse(@PathVariable(name = "courseId", required = true) Integer courseId) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        try {
            // Vérifier si courseId existe
            if (!courseService.existsById(courseId)) {
                String errorMessage = ("Course with id " + courseId + " not found.");
                responseAsMap.put("Error message", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            } else {
                List<Student> studentsByCourse = studentService.findStudentsByCoursesId(courseId);

                List<StudentDto> dtos = new ArrayList<>();
                ModelMapper modelMapper = new ModelMapper();
                for( Student student : studentsByCourse) {
                    dtos.add(modelMapper.map(student, StudentDto.class));                    
                }
                    
                String succesMessage = ("Students from de course with id " + courseId);
                responseAsMap.put("Success message: ", succesMessage);
                responseAsMap.put ("List found", dtos);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
             }
            
        } catch (DataAccessException e) {
            String errorGrave = "Error found for students by course with id : " + courseId;
            responseAsMap.put("Serious error" , errorGrave);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }


    // GET Récupérer tous les cours futurs d'un étudiant
    @GetMapping("/students/{globalId}/courses")
    public ResponseEntity<Map<String, Object>> getCoursesByStudentGlobalId(@PathVariable(name = "globalId", required = true) String globalId) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        try {
            // Vérifier si globlaId existe
            Student globalIdStudent = studentService.findStudentByGlobalId(globalId);

            if(globalIdStudent !=null) {
                Set<Course> coursesByStudent = globalIdStudent.getCourses();

                List<CourseStudentDto> dtos = new ArrayList<>();
                ModelMapper modelMapper = new ModelMapper();

                for (Course course : coursesByStudent) {
                    dtos.add(modelMapper.map(course, CourseStudentDto.class));
                }

                responseAsMap.put ("List found", dtos);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);
                
            } else {
                String errorMessage = ("This globalId does not exist");
                responseAsMap.put("Error message", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            }
            
        } catch (DataAccessException e) {
            String errorGrave = "Failed request " + e.getMostSpecificCause();
            responseAsMap.put("Serious error" , errorGrave);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
    

}