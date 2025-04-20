package com.example.project.service.impl;

import com.example.project.model.Profile;
import com.example.project.service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Override
    public List<Profile> findAll() {
        return List.of();
    }

    @Override
    public Profile findById(UUID uuid) {
        return null;
    }

    @Override
    public Profile save(Profile profile) {
        return null;
    }

    @Override
    public Profile deleteById(UUID uuid) {
        return null;
    }

    @Override
    public Profile update(Profile profile, UUID uuid) {
        return null;
    }
}
