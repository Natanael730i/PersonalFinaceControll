package com.example.project.service.impl;

import com.example.project.dao.CategoryDao;
import com.example.project.model.Category;
import com.example.project.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public List<Category> findAll() {
        return List.of();
    }

    @Override
    public Category findById(UUID uuid) {
        return null;
    }

    @Override
    public Category save(Category category) {
        return null;
    }

    @Override
    public Category deleteById(UUID uuid) {
        return null;
    }

    @Override
    public Category update(Category category, UUID uuid) {
        return null;
    }
}
