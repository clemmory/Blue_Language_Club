package com.blueLanguageClub.controllers;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
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
import com.blueLanguageClub.dto.CourseAdminDto;
import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.Student;
import com.blueLanguageClub.services.CourseService;
import com.blueLanguageClub.services.StudentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;

    // Affficher tous les cours - OK
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> findAllCourses() {

        List<Course> courses = courseService.findAllCourses();
        return new ResponseEntity<>(courses, HttpStatus.OK);

    }

    //ADMIN - Enregistrer un cours - OK
    @PostMapping("/courses")
    @Transactional
    public ResponseEntity<Map<String, Object>> saveCourse(
            @Valid @RequestBody Course course, BindingResult validationResults) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;


        // Vérifier si le cours à enregistrer comporte des erreurs
        if (validationResults.hasErrors()) {
            List<String> errorsList = new ArrayList<>();

            List<ObjectError> objectErrors = validationResults.getAllErrors();
            objectErrors.forEach(objectError -> errorsList.add(objectError.getDefaultMessage()));

            responseAsMap.put("error", errorsList);
            responseAsMap.put("Incorrect course", course);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        try { 
            if(!courseService.isCourseInFuture(course)){
                String errorMessage = "You cannot add at an earlier date/time, please change date/time.";
                responseAsMap.put("errorMessage", errorMessage);
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
            } else {
                Course savedCourse = courseService.saveCourse(course);
                String successMessage = "The course has been added successfully.";
                responseAsMap.put("Success message", successMessage);
                responseAsMap.put("Saved course", savedCourse);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
            }
        } catch (DataAccessException e) {
            String error = "Failed to add new course " + e.getMostSpecificCause();
            responseAsMap.put("error", error);
            responseAsMap.put("Course not saved", course);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }


    //ADMIN DELETE Supprimer un cours {/api/courses/{idCourse}}
    @DeleteMapping("/courses/{idCourse}")
    public ResponseEntity<Map<String, Object>> deleteCourseById(
            @PathVariable(name = "idCourse", required = true) Integer idCourse) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        try {
            Course course = courseService.findByIdCourse(idCourse);
            if (course != null) {
                if (!courseService.isCourseInFuture(course)) {
                    String errorMessage = "You can't delete this course because it has already ended";
                    responseAsMap.put("errorMessage", errorMessage);
                    responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
                } else {
                    courseService.deleteById(idCourse);
                    String successMessage = "The course with id " + idCourse + " has been deleted.";
                    responseAsMap.put("successMessage", successMessage);
                    responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);
                }
            } else {
                String errorMessage = "Course with id : " + idCourse + " not found";
                responseAsMap.put("errorMessage", errorMessage);
                responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            String seriousError = "An error occurred while deleting the course with id " + idCourse
                    + ", and the most probable cause is " + e.getMostSpecificCause();
            responseAsMap.put("seriousError", seriousError);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    //ADMIN PUT Modifier un cours {/api/courses/{idCourse}} OK
    @PutMapping("/courses/{idCourse}")
    public ResponseEntity<Map<String, Object>> updatedCourse(@Valid @RequestBody Course course,
            BindingResult validationResults, @PathVariable(name = "idCourse", required = true) Integer idCourse) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Vérifier si les données apportées sont conformes
        if (validationResults.hasErrors()) {
            List<String> errores = new ArrayList<>();
            List<ObjectError> objectErrors = validationResults.getAllErrors();
            objectErrors.forEach(objectError -> errores.add(objectError.getDefaultMessage()));

            responseAsMap.put("errors", errores);
            responseAsMap.put("Not built course", course);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }

        // S'il ya pas des erreurs dans le cours,on modifie le curs
       
        try {
            Course updatedCourse = courseService.findByIdCourse(idCourse);
            //Vérifier si le cours  à modifier existe
            if(updatedCourse != null) {
                //Vérification que l'heure ne soit pas passée si la date est celle du jour
                if (!courseService.isCourseInFuture(course)) {
                    String errorMessage = "You can't update this course with an anterior date";
                    responseAsMap.put("errorMessage", errorMessage);
                    responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
                } else {
                    updatedCourse.setDate(course.getDate());
                    updatedCourse.setTime(course.getTime());
                    updatedCourse.setMode(course.getMode());
                    updatedCourse.setPlace(course.getPlace());  
                    courseService.saveCourse(updatedCourse);

                    String successMessage = "The course has been updated successfully";
                    responseAsMap.put("successMessage", successMessage);
                    responseAsMap.put("Updated course", updatedCourse);
                    responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.CREATED);
                }
            //Erreur si le cours à modifier n'existe pas 
            } else {
                String errorMessage = "The course with Id:" +idCourse + "is not found";
                responseAsMap.put("errorMessage", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException e) {
            String error = "Error when trying to update course and the most common cause "
                    + e.getMostSpecificCause();
            responseAsMap.put("Error", error);
            responseAsMap.put("Course tried to be updated ", course);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }
  

    
    //ATTENDEE GET An attendee can list all available classes for future dates {students/{globalid}/courses}

    //Cours sorted by dates 
    @GetMapping("/students/{globalId}/courses")
    public ResponseEntity<Map<String, Object>> findAllCoursesforStudents(
        @PathVariable(name = "globalId", required = true) String globalId) {
        

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        Sort sortByDate = Sort.by("date");


    try {
        Student student = studentService.findStudentByGlobalId(globalId);
        //Si l'étudiant existe je cherche les cours 
        if (student != null) {
            //Cours existants par dates
            List<Course>existingCourses = courseService.findAllCoursesSorted(sortByDate);

            //Je filtre les cours existants pour ne montrer que ceux à une future date
            List<Course> coursesFuture = existingCourses.stream()
            .filter(c -> courseService.isCourseInFuture(c) == true ) 
            .collect(Collectors.toList());


            List<CourseStudentDto> dtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();

            for (Course course : coursesFuture) {
                dtos.add(modelMapper.map(course, CourseStudentDto.class));
            }

            String successMessage = "Student with globalId: " + globalId + " found.";
            responseAsMap.put("successMessage", successMessage);
            responseAsMap.put("List courses", dtos);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);
        } else {
            String errorMessage = "The student with globalId:" + globalId + "is not found, please ask for registration.";
            responseAsMap.put("errorMessage", errorMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND); 
        }
        
    } catch (DataAccessException e) {
        String error = "Error when trying to update course and the most common cause "+ e.getMostSpecificCause();   
        responseAsMap.put("Error", error);
        responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
        
        return responseEntity;
    }

    //ADMIN GET Récuérer tous les cours disponibles à une date postérieure
    @GetMapping("/courses/available")
    public ResponseEntity<Map<String, Object>>getAvailableCoursesForFutureDates() {


    Map<String,Object> responseAsMap = new HashMap<>();
    ResponseEntity<Map<String,Object>> responseEntity = null;
    Sort sortByDate = Sort.by("date");

  
    try {
        //Récupérer les cours existants
        List<Course> existingCourses = courseService.findAllCoursesSorted(sortByDate);

        if (!existingCourses.isEmpty()) {
            //Filtrer les cours seulement à une date future
            List<Course> availableCourses = existingCourses.stream()
                    .filter(course -> courseService.isCourseInFuture(course))
                    .collect(Collectors.toList());
        
            List<CourseAdminDto> dtos = new ArrayList<>();
            ModelMapper modelMapper = new ModelMapper();

                for(Course course : availableCourses){
                    dtos.add(modelMapper.map(course, CourseAdminDto.class));
                } 
            responseAsMap.put("List of available courses", dtos);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.OK);

        } else {
            //Si il n'y a aucun cours disponible
            String error = "There are no courses available, please add course.";
            responseAsMap.put("Error diplaying courses", error);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND);
        }
        } catch (DataAccessException e) {
            String errorMessage = "Failed request" + e.getMostSpecificCause();
            responseAsMap.put("errorMessage", errorMessage);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;

    }

    


}


