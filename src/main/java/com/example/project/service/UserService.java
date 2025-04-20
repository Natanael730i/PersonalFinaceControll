package com.example.project.service;

import com.example.project.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    User findById(UUID id);
    User save(User user);
    User deleteById(UUID id);
    User findByEmail(String email);
    User update(User user,  UUID id);
}
