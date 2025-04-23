package com.example.project.config;

import com.example.project.dao.ProfileDao;
import com.example.project.dao.UserDao;
import com.example.project.model.Profile;
import com.example.project.model.User;
import com.example.project.model.enums.RoleType;
import com.example.project.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DataInitializer {

    private final ProfileDao profileDao;
    private final UserDao userDao;

    public DataInitializer(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    @PostConstruct
    public void init() {
        // Criação dos perfis caso não existam
        Profile adminProfile = profileDao.findByName("ADMIN").orElseGet(() -> {
            Profile profile = new Profile();
            profile.setName("ADMIN");
            profile.setRole(RoleType.ADMIN);
            profile.setCode(1L);
            return profileDao.save(profile);
        });

        Profile userProfile = profileDao.findByName("USER").orElseGet(() -> {
            Profile profile = new Profile();
            profile.setCode(2L);
            profile.setRole(RoleType.USER);
            profile.setName("USER");
            return profileDao.save(profile);
        });

        // Criação do usuário padrão USER caso não exista
        userDao.findByEmail("admin").orElseGet(() -> {
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail("admin");
            user.setPassword(Utils.hashPassword("admin"));
            user.setName("admin");
            user.setProfiles(adminProfile);
            return userDao.save(user);
        });

        userDao.findByEmail("user").orElseGet(() -> {
            User user = new User();
            user.setId(UUID.randomUUID());
            user.setEmail("user");
            user.setPassword(Utils.hashPassword("user"));
            user.setName("user");
            user.setProfiles(userProfile);
            return userDao.save(user);
        });


        System.out.println("Perfis e usuários padrão verificados/criados com sucesso.");
    }
}
