package com.example.project.service;

import com.example.project.dto.LoginDto;
import com.example.project.model.User;

import java.util.UUID;

public interface UserService extends GenericService<User, UUID> {
    String login(LoginDto user) throws Exception;
}
