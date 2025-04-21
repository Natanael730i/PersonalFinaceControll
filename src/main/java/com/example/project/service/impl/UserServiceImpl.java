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

        if (user.getProfiles() != null && !user.getProfiles().isEmpty()) {
            for (Profile profile : user.getProfiles()) {
                if (profile.getId() != null) {
                    profileDao.findById(profile.getId()).ifPresent(profiles::add);
                }
                else if (profile.getCode() != null) {
                    Profile foundByCode = profileDao.findByCode(profile.getCode());
                    if (foundByCode != null) {
                        profiles.add(foundByCode);
                    }
                }
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
    public User update(UserDto dto, UUID id) {
        User oldUser = userDao.findById(id).orElseThrow();

        if (dto.password() != null && !dto.password().isBlank()) {
            oldUser.setPassword(Utils.hashPassword(dto.password()));
        }

        if (dto.name() != null) oldUser.setName(dto.name());
        if (dto.email() != null) oldUser.setEmail(dto.email());

        if (dto.profileIds() != null && !dto.profileIds().isEmpty()) {
            Set<Profile> profiles = dto.profileIds().stream()
                    .map(profileDao::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());
            oldUser.setProfiles(profiles);
        }

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

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
