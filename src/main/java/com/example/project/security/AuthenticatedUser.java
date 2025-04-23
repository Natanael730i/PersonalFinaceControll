package com.example.project.security;

import com.example.project.model.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {

    public static User get() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        throw new IllegalStateException("Usuário não autenticado.");
    }
}
