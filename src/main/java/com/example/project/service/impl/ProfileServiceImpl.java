package com.example.project.service.impl;

import com.example.project.dao.ProfileDao;
import com.example.project.model.Profile;
import com.example.project.service.ProfileService;
import com.example.project.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileDao profileDao;

    public ProfileServiceImpl(ProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public List<Profile> findAll() {
        return (List<Profile>) profileDao.findAll();
    }

    @Override
    public Profile findById(UUID id) {
        return profileDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Profile not found!"));
    }

    @Override
    public Profile save(Profile profile) {
        return profileDao.save(profile);
    }

    @Override
    public void deleteById(UUID id) {
        Profile profile = profileDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Profile not found!"));
        if (profile != null) {
            profileDao.delete(profile);
        }
    }

    @Override
    public Profile update(Profile profile, UUID uuid) {
        Profile oldProfile = profileDao.findById(uuid).orElse(null);
        if (oldProfile == null) {
            return null;
        }
        Utils.copyNonNullProperties(oldProfile, profile);
        return profileDao.save(oldProfile);
    }
}
