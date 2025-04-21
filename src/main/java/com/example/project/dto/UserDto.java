package com.example.project.dto;

import java.util.Set;
import java.util.UUID;

public record UserDto(
        String name,
        String email,
        String password,
        Set<UUID> profileIds
) {}
