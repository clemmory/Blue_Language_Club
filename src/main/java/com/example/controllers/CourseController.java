package com.example.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.CourseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

private final CourseService courseService;

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCourseById(@PathVariable(name = "id", required = true) Integer idCourse) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        try {

            courseService.deleteCourse(courseService.findByIdCourse(idCourse));
            String successMessage = "The course with id " + idCourse + " has been deleted.";
            responseAsMap.put("successMessage", successMessage);
            responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);

        } catch (DataAccessException e) {

            String seriousError = "An error occurred while deleting the product with id " + idCourse + ", and the most probable cause is " + e.getMostSpecificCause();
            responseAsMap.put("seriousError", seriousError);
            responseEntity = new ResponseEntity<Map<String,Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

}
