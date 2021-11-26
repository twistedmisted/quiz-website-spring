package com.epam.final_project.service;

import com.epam.final_project.entity.CategoryEntity;
import com.epam.final_project.repository.CategoryRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryEntity> getAll() {
        return categoryRepository.findAll();
    }

}
