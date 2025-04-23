package com.example.project.model;

import com.example.project.model.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@Entity
@Table(name = "PROFILE")
public class Profile implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private Long code;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType role;

    @Override
    public String getAuthority() {
        return "ROLE_" + role.name();
    }
}

