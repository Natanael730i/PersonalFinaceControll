package com.example.project.service;

import java.util.List;

public interface GenericService<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T save(T t);
    void deleteById(ID id);
    T update(T t,  ID id);
}
