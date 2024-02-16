package com.example.services;

import java.util.List;

import com.example.entities.User;

public interface UserService {

    //Créer un participant
    public User saveUser(User user);

    //Afficher un participant grace a son id
    public User findByUser(int idUser);

    //Supprimer un participant
    public void deleteUser(User user);

    //Afficher tous les participants
    public List<User> findAllUsers();

}
