package com.example.project.controller;

import com.example.project.dto.LoginDto;
import com.example.project.dto.UserDto;
import com.example.project.model.User;
import com.example.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDto user) {
        try {
            return ResponseEntity.ok(service.login(user));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody UserDto t) {
        return ResponseEntity.ok(service.update(t, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}
