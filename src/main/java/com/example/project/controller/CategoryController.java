package com.example.project.controller;

import com.example.project.model.Category;
import com.example.project.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/category")
@Tag(name = "Category Controller")
public class CategoryController extends GenericController<Category, UUID> {

    public CategoryController(CategoryService categoryService) {
        super(categoryService);
    }
}
