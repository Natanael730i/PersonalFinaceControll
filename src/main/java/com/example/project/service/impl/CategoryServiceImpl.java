package com.example.project.service.impl;

import com.example.project.dao.CategoryDao;
import com.example.project.model.Category;
import com.example.project.service.CategoryService;
import com.example.project.utils.Utils;
import jakarta.persistence.EntityNotFoundException;
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
        return (List<Category>) categoryDao.findAll();
    }

    @Override
    public Category findById(UUID uuid) {
        return categoryDao.findById(uuid).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public void deleteById(UUID id) {
        Category category = categoryDao.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found!"));
        if (category != null) {
            categoryDao.delete(category);
        }
    }

    @Override
    public Category update(Category category, UUID uuid) {
        Category oldCategory = categoryDao.findById(uuid).orElse(null);
        if (oldCategory == null) {
            return null;
        }
        Utils.copyNonNullProperties(category, oldCategory);
        return categoryDao.save(oldCategory);
    }
}
