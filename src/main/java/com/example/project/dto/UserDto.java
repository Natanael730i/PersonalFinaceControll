package com.example.project.dto;

import com.example.project.model.Profile;

import java.util.Set;
import java.util.UUID;

public record UserDto(
        String name,
        String email,
        String password,
        Profile profileIds
) {}
