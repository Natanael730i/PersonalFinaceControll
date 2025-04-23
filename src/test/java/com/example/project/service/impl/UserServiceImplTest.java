package com.example.project.service.impl;

import com.example.project.dao.ProfileDao;
import com.example.project.dao.UserDao;
import com.example.project.dto.LoginDto;
import com.example.project.model.Profile;
import com.example.project.model.User;
import com.example.project.model.enums.RoleType;
import com.example.project.security.JwtConfig;
import com.example.project.service.UserService;
import com.example.project.utils.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    private UserDao userDao;
    private JwtConfig jwtConfig;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDao.class);
        ProfileDao profileDao = Mockito.mock(ProfileDao.class);
        jwtConfig = Mockito.mock(JwtConfig.class);
        userService = new UserServiceImpl(profileDao, userDao, jwtConfig);
    }

    @Test
    public void deveRetornarTrueSeEmailJaExistir() {
        // Arrange
        UserDao mockUserDao = Mockito.mock(UserDao.class);
        UserServiceImpl userService = new UserServiceImpl(null, mockUserDao, null); // null para o ProfileDao

        String email = "teste@email.com";
        Mockito.when(mockUserDao.findByEmail(email)).thenReturn(Optional.of(new User()));

        // Act
        boolean resultado = userService.emailJaCadastrado(email);

        // Assert
        assertTrue(resultado);
    }

    @Test
    public void deveRetornarFalseSeEmailNaoExistir() {
        UserDao mockUserDao = Mockito.mock(UserDao.class);
        UserServiceImpl userService = new UserServiceImpl(null, mockUserDao, null);

        String email = "naoexiste@email.com";
        Mockito.when(mockUserDao.findByEmail(email)).thenReturn(Optional.empty());

        boolean resultado = userService.emailJaCadastrado(email);

        assertFalse(resultado);
    }

    @Test
    void testLoginComCredenciaisValidas(){
        String email = "admin";
        String password = "admin";
        String passwordHash = Utils.hashPassword(password);
        String simulatedToken = "jwr_Token_123";

        User user = createUser(email, passwordHash);

        when(userDao.findByEmail(email)).thenReturn(Optional.of(user));
        when(jwtConfig.createJWT(user)).thenReturn(simulatedToken);

        LoginDto loginDto = new LoginDto(email, password);

        String token = userService.login(loginDto);

        assertEquals(simulatedToken, token);
    }

    @Test
    void testLoginComSenhaInvalida() {
        String email = "teste@email.com";
        String senhaErrada = "$2a$10$kVhSOpF1v3Yq4Hh1H87dQeDCN2Y7Zqx3B/5OWpeA2n1y92CQekj.q";

        User user = createUser(email, senhaErrada);

        LoginDto userDto = new LoginDto(email, senhaErrada);

        when(userDao.findByEmail(email)).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login(userDto);
        });

        assertEquals("Login data not valid", exception.getMessage());
    }

    @Test
    void testLoginComEmailInexistente() {
        String email = "inexistente@email.com";

        when(userDao.findByEmail(email)).thenReturn(Optional.empty());
        User user = createUser("qualqueremail", "qualquerSenha");
        LoginDto userDto = new LoginDto(user.getEmail(), "qualquerSenha");
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.login(userDto);
        });

        assertEquals("Email not found!", exception.getMessage());
    }

    User createUser(String email, String passwordHash) {
        Profile profile = new Profile();
        profile.setRole(RoleType.ADMIN);
        profile.setCode(1L);
        profile.setId(UUID.randomUUID());
        profile.setName("admin");

        User user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail(email);
        user.setPassword(passwordHash);
        user.setName("admin");
        user.setProfiles(profile);
        return user;
    }

}
