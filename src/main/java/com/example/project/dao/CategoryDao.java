package com.example.project.dao;

import com.example.project.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryDao extends CrudRepository<Category, UUID> {
}
