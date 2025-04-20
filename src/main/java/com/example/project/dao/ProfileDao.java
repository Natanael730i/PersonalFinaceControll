package com.example.project.dao;

import com.example.project.model.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProfileDao extends CrudRepository<Profile, UUID> {
}
