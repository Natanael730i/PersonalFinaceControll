package com.example.project.controller;

import com.example.project.service.GenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GenericController<T, ID> {

    private final GenericService<T, ID> service;

    public GenericController(GenericService<T, ID> service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable ID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T user) {
        return ResponseEntity.ok(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T t) {
        return ResponseEntity.ok(service.update(t, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<T> delete(@PathVariable ID id) {
        return ResponseEntity.ok(service.deleteById(id));
    }
}
