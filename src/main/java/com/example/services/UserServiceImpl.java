package com.example.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.entities.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserDao userDao;
    
    @Override
    public User saveUser(User user) {
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
