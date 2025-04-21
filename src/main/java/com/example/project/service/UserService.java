package com.example.project.service;

import com.example.project.dto.LoginDto;
import com.example.project.dto.UserDto;
import com.example.project.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    String login(LoginDto user) throws Exception;
    User getUserByEmail(String email);
    User update(UserDto dto, UUID id);
    List<User> findAll();
    User findById(UUID id);
    User save(User t);
    User deleteById(UUID id);
}
