package com.example.project.service.impl;

import com.example.project.dao.ProfileDao;
import com.example.project.dao.UserDao;
import com.example.project.dto.LoginDto;
import com.example.project.model.Profile;
import com.example.project.model.User;
import com.example.project.security.JwtConfig;
import com.example.project.service.UserService;
import com.example.project.utils.Utils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        Set<Profile> profiles = new HashSet<>();

        if (user.getProfiles() != null) {
            for (Profile profile : user.getProfiles()) {
                profileDao.findById(profile.getId()).ifPresent(profiles::add);
            }
        }
        user.setProfiles(profiles);
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
    public String login(LoginDto login) throws Exception {
        User user = userDao.findByEmail(login.email());

        if (user == null || !Utils.validatePassword(login.password(), user.getPassword())) {
            throw new Exception("Usuário ou senha inválidos.");
        }

        return jwtConfig.createJWT(user);
    }
}
