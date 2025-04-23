package com.example.project.config;

import com.example.project.dao.ProfileDao;
import com.example.project.dao.UserDao;
import com.example.project.model.Profile;
import com.example.project.model.User;
import com.example.project.model.enums.RoleType;
import com.example.project.utils.Utils;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

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

        // Criação do usuário ADMIN caso não exista
        if (!userDao.existsByEmail("admin")) {
            User admin = new User();
            admin.setName("Administrador do Sistema");
            admin.setEmail("admin@system.com");
            admin.setPassword(Utils.hashPassword("admin123"));
            admin.setProfiles(adminProfile);
            userDao.save(admin);
        }

        // Criação do usuário padrão USER caso não exista
        if (!userDao.existsByEmail("user@system.com")) {
            User user = new User();
            user.setName("Usuário Padrão");
            user.setEmail("user@system.com");
            user.setPassword(Utils.hashPassword("user123"));
            user.setProfiles(userProfile);
            userDao.save(user);
        }

        System.out.println("Perfis e usuários padrão verificados/criados com sucesso.");
    }
}
