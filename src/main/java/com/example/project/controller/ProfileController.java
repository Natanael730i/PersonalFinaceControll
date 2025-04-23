package com.example.project.controller;

import com.example.project.model.Profile;
import com.example.project.service.ProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/profile")
@Tag(name = "Profile Controller")
public class ProfileController extends GenericController<Profile, UUID> {

    public ProfileController(ProfileService service) {
        super(service);
    }
}

