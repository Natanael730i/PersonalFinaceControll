package com.example.project.dao;

import com.example.project.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserDao extends CrudRepository<User, UUID> {
    User findByEmail(String email);

    boolean existsByEmail(String mail);
}
