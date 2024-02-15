package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entities.User;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

}
