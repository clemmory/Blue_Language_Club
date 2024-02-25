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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueLanguageClub.entities.Course;
import com.blueLanguageClub.services.CourseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // Affficher tous les cours
    @GetMapping("/courses")

    public ResponseEntity<List<Course>> findAllCourses() {

        List<Course> courses = courseService.findAllCourses();

        return new ResponseEntity<>(courses, HttpStatus.OK);

    }

    // Enregistrer un curs
    @PostMapping("/courses")
    @Transactional
    public ResponseEntity<Map<String, Object>> saveCourse(
            @Valid @RequestBody Course course, BindingResult validationResults) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Vérifier si l'objet curs comporte des erreurs
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
            String error = "Failed to add new user " + e.getMostSpecificCause();
            responseAsMap.put("error", error);
            responseAsMap.put("Course not saved", course);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    // Modifier un cours

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatedCourse(@Valid @RequestBody Course course,
                    BindingResult validationResults, @PathVariable(name = "id", required = true) Integer idCourse) {
 
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
        // Verifier si le curs a des érreurs
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
       
        //S'il ya pas des erreurs dans le curs,on modifie le curs

        try {Course updatedCourse = courseService.findByIdCourse(idCourse);
            if(updatedCourse != null) {
                updatedCourse.setTitle(course.getTitle());
                updatedCourse.setDate(course.getDate());
                updatedCourse.setTime(course.getTime());
                updatedCourse.setMode(course.getMode());
                updatedCourse.setPlace(course.getPlace());
                updatedCourse.setLanguage(course.getLanguage());
                updatedCourse.setLevel(course.getLevel());
                updatedCourse.setMax_students(course.getMax_students());
                     
            String successMessage = "The course has been updated successfully";
            responseAsMap.put("successMessage", successMessage);
            responseAsMap.put("Updated course", updatedCourse);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.CREATED);
            }
            else{
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourseById(
            @PathVariable(name = "id", required = true) Integer idCourse) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        try {

            courseService.deleteCourse(courseService.findByIdCourse(idCourse));
            String successMessage = "The course with id " + idCourse + " has been deleted.";
            responseAsMap.put("successMessage", successMessage);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);

        } catch (DataAccessException e) {

            String seriousError = "An error occurred while deleting the product with id " + idCourse
                    + ", and the most probable cause is " + e.getMostSpecificCause();
            responseAsMap.put("seriousError", seriousError);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
