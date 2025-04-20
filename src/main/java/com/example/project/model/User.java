package com.example.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "'USER'")
public class User {

    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;

}
