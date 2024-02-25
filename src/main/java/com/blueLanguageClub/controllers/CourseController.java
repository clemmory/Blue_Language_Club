package com.blueLanguageClub.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.query.Param;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.entities.LANGUAGE;
import com.blueLanguageClub.services.CourseService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    //GET Affficher tous les cours disponibles {/api/courses} 
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> findAllCourses(
        @RequestParam(name = "language", required = false) LANGUAGE language){
        
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now(); 
        List<Course>existingCourses = new ArrayList<Course>();

        //Je fais une recherche génerale ou par language
        if(language == null){
            courseService.findAllCourses().forEach(existingCourses::add);
        } else {
            courseService.findCoursesByLanguage(language).forEach(existingCourses::add);
        }

        List<Course> courses = existingCourses.stream()
        .filter(c -> c.getDate().isAfter(today) || (c.getDate().isEqual(today) && c.getTime().isAfter(now)))
        .collect(Collectors.toList());

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    //Enregistrer un cours - OK
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
            Course savedCourse = courseService.saveCourse(course);
            String successMessage = "The course has been added successfully.";
            responseAsMap.put("Success message", successMessage);
            responseAsMap.put("Saved course", savedCourse);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String error = "Failed to add new course " + e.getMostSpecificCause();
            responseAsMap.put("error", error);
            responseAsMap.put("Course not saved", course);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }


    //DELETE Supprimer un cours {/api/courses/{idCourse}}
    @DeleteMapping("/courses/{idCourse}")
    public ResponseEntity<Map<String, Object>> deleteCourseById(@PathVariable(name = "idCourse", required = true) Integer idCourse) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        try {
            Course course = courseService.findByIdCourse(idCourse);
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            if (course != null) {
                if (course.getDate().isBefore(today)|| course.getDate().isEqual(today) && course.getTime().isBefore(now)) {
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
            String seriousError = "An error occurred while deleting the course with id " + idCourse + ", and the most probable cause is " + e.getMostSpecificCause();
            responseAsMap.put("seriousError", seriousError);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    //PUT Modifier un cours {/api/courses/{idCourse}} OK
    @PutMapping("/courses/{idCourse}")
    public ResponseEntity<Map<String, Object>> updatedCourse(@Valid @RequestBody Course course,
                    BindingResult validationResults, @PathVariable(name = "idCourse", required = true) Integer idCourse) {
 
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Vérifier si les données apportées sont conformes
        if (validationResults.hasErrors()) {
            List<String> errores =  new ArrayList<>();
            List<ObjectError> objectErrors = validationResults.getAllErrors();
            objectErrors.forEach(objectError ->
                errores.add(objectError.getDefaultMessage()));
 
            responseAsMap.put("errors", errores);
            responseAsMap.put("Not built course", course);
 
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
 
            return responseEntity;
        }
       
        try {
            LocalDate today = LocalDate.now();
            LocalTime now = LocalTime.now();
            Course updatedCourse = courseService.findByIdCourse(idCourse);
            //Vérifier si le cours  à modifier existe
            if(updatedCourse != null) {
                //Vérification que l'heure ne soit pas passée si la date est celle du jour
                if (course.getDate().isEqual(today) && course.getTime().isBefore(now)) {
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
                responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.NOT_FOUND); 
            }
        } catch (DataAccessException e) {
            String error = "Error when trying to update course and the most common cause "
                            + e.getMostSpecificCause();   
            responseAsMap.put("Error", error);
            responseAsMap.put("Course tried to be updated ", course);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
       
        return responseEntity;
    }
  

    
    // GET Retrieved all students of a specific course Rosa

    //GET ATTENDEE An attendee can list all available classes for future dates {students/{globalid}/courses}
    //Only classes that are relevant to this student ? based on his level?

    


}


