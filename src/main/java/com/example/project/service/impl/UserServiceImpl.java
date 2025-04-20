package com.example.project.service.impl;

import com.example.project.dao.UserDao;
import com.example.project.dto.LoginDto;
import com.example.project.model.User;
import com.example.project.security.JwtConfig;
import com.example.project.service.UserService;
import com.example.project.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authManager;
    private final UserDao userDao;

    public UserServiceImpl(AuthenticationManager authManager, UserDao userDao) {
        this.authManager = authManager;
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userDao.findAll();
    }

    @Override
    public User findById(UUID id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User save(User user) {
        user.setPassword(Utils.hashPassword(user.getPassword()));
        return userDao.save(user);
    }

    @Override
    public User deleteById(UUID id) {
        User user = userDao.findById(id).orElse(null);
        if (user != null) {
            userDao.delete(user);
            return new User();
        }
        return null;
    }

    @Override
    public User update(User user, UUID id) {
        User oldUser = userDao.findById(id).orElse(null);
        if (oldUser == null) {
            return null;
        }
        Utils.copyNonNullProperties(user, oldUser);
        return userDao.save(oldUser);
    }

    @Override
    public String login(LoginDto user) throws Exception {
        JwtConfig jwtConfig = new JwtConfig();
        UsernamePasswordAuthenticationToken loginData = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        try {
            Authentication authentication = authManager.authenticate(loginData);
            return jwtConfig.createJWT(authentication);
        } catch (AuthenticationException e) {
            throw new Exception(e.getMessage());
        }
    }
}
