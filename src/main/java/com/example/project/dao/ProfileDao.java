package com.example.project.dao;

import com.example.project.model.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProfileDao extends CrudRepository<Profile, UUID> {
    Optional<Profile> findByName(String admin);

    Profile findByCode(Long code);
}
