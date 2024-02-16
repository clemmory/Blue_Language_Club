package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;


import com.example.dao.UserDao;
import com.example.entities.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserDao userDao;
    
    @Override
    public User createUser(User user) {
        return userDao.save(user);
     
    }

    @Override
    public User findByUser(int idUser) {
        return userDao.findById(idUser).get();

    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);

    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

}
