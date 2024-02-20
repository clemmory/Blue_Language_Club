// package com.example.controllers;

// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import org.springframework.dao.DataAccessException;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.validation.BindingResult;
// import org.springframework.validation.ObjectError;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.entities.Course;
// import com.example.entities.User;
// import com.example.services.CourseService;
// import com.example.services.UserService;

// import jakarta.validation.Valid;

// @RestController
// @RequestMapping("/courses")
// public class CourseController {

// //     private final CourseService courseService;

// //     //Enregistrer un curs
// //     @PostMapping
// //     @Transactional
// //     public ResponseEntity<Map<String, Object>> saveCourse(
// //             @Valid @RequestBody Course course, BindingResult validationResults) {

// //         Map<String, Object> responseAsMap = new HashMap<>();
// //         ResponseEntity<Map<String, Object>> responseEntity = null;

// //         // Vérifier si l'objet curs comporte des erreurs
// //         if (validationResults.hasErrors()) {
// //             List<String> errorsList = new ArrayList<>();

// //             List<ObjectError> objectErrors = validationResults.getAllErrors();
// //             objectErrors.forEach(objectError -> errorsList.add(objectError.getDefaultMessage()));

// //             responseAsMap.put("error", errorsList);
// //             responseAsMap.put("Incorrect course", course);

// //             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

// //             return responseEntity;
// //         }
// //         try {
// //             Course savedCourse = courseService.saveCourse(course);
// //             String successMessage = "The course has been added successfully.";
// //             responseAsMap.put("Success message", successMessage);
// //             responseAsMap.put("Saved course", savedCourse);
// //             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
// //         } catch (DataAccessException e) {
// //             String error = "Failed to add new user " + e.getMostSpecificCause();
// //             responseAsMap.put("error", error);
// //             responseAsMap.put("Course not saved", course);
// //             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
// //         }

// //         return responseEntity;

// //     }

// //     //Afficher tous les curses
// //     @GetMapping
// //     public ResponseEntity<List<Course>> findAllCourses(){

// //         ResponseEntity<List<Course>> responseEntity = null;

// //         List<Course> courses = courseService.findAllCourses();
// //         responseEntity = new ResponseEntity<List<Course>>(courses,HttpStatus.OK);

// //         return responseEntity;
// //     }

//  }



