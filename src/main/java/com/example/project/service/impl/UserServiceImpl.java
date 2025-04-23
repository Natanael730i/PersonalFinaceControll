package com.example.project.service.impl;

import com.example.project.dao.ProfileDao;
import com.example.project.dao.UserDao;
import com.example.project.dto.LoginDto;
import com.example.project.dto.UserDto;
import com.example.project.model.Profile;
import com.example.project.model.User;
import com.example.project.security.JwtConfig;
import com.example.project.service.UserService;
import com.example.project.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final ProfileDao profileDao;
    private final UserDao userDao;
    private final JwtConfig  jwtConfig;

    public UserServiceImpl(ProfileDao profileDao, UserDao userDao, JwtConfig jwtConfig) {
        this.profileDao = profileDao;
        this.userDao = userDao;
        this.jwtConfig = jwtConfig;
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

        Profile profiles = new Profile();

        if (user.getProfiles().getId() != null) {
            profiles = profileDao.findById(user.getProfiles().getId()).orElse(null);
        }
        if (user.getProfiles().getCode() != null) {
            Profile foundByCode = profileDao.findByCode(user.getProfiles().getCode());
            if (foundByCode != null) {
                profiles = foundByCode;
            }
        }

        user.setProfiles(profiles);

        return userDao.save(user);
    }

    @Override
    public void deleteById(UUID id) {
        User user = userDao.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found!"));
        userDao.delete(user);
    }

    @Override
    public boolean emailJaCadastrado(String email) {
        return userDao.findByEmail(email).isPresent();
    }

    @Override
    public User update(UserDto dto, UUID id) {
        User oldUser = userDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));

        if (dto.password() != null && !dto.password().isBlank()) {
            oldUser.setPassword(Utils.hashPassword(dto.password()));
        }

        if (dto.name() != null) oldUser.setName(dto.name());
        if (dto.email() != null) oldUser.setEmail(dto.email());

        if (dto.profileIds() != null ) oldUser.setProfiles(dto.profileIds());

        return userDao.save(oldUser);
    }

    @Override
    public String login(LoginDto login) {
        User user = userDao.findByEmail(login.email())
                .orElseThrow(() -> new EntityNotFoundException("Email not found!"));

        if (user == null || !Utils.validatePassword(login.password(), user.getPassword())) {
            throw new EntityNotFoundException("Login data not valid");
        }

        return jwtConfig.createJWT(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() ->new EntityNotFoundException("Email not found!"));
    }
}
