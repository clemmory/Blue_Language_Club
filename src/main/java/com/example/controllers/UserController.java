package com.example.controllers;

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

import com.example.entities.User;
import com.example.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //Enregistrer un user 
    @PostMapping
    @Transactional
    public ResponseEntity<Map<String, Object>> saveUser(
            @Valid @RequestBody User user, BindingResult validationResults) {

        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;

        // Vérifier si l'objet user comporte des erreurs
        if (validationResults.hasErrors()) {
            List<String> errorsList = new ArrayList<>();

            List<ObjectError> objectErrors = validationResults.getAllErrors();
            objectErrors.forEach(objectError -> errorsList.add(objectError.getDefaultMessage()));

            responseAsMap.put("error", errorsList);
            responseAsMap.put("User incorrect", user);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        try {
            User savedUser = userService.saveUser(user);
            String successMessage = "The user has been added successfully.";
            responseAsMap.put("Success message", successMessage);
            responseAsMap.put("Saved user", savedUser);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String error = "Failed to add new user " + e.getMostSpecificCause();
            responseAsMap.put("error", error);
            responseAsMap.put("User not saved", user);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;

    }

    //Afficher tous les users
    @GetMapping
    public ResponseEntity<List<User>> findAllUsers(){

        ResponseEntity<List<User>> responseEntity = null;

        List<User> users = userService.findAllUsers();
        responseEntity = new ResponseEntity<List<User>>(users,HttpStatus.OK);

        return responseEntity;
    }

}
