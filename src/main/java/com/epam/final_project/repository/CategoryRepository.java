package com.epam.final_project.repository;

import com.epam.final_project.entity.CategoryEntity;
import com.epam.final_project.entity.DifficultyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    List<CategoryEntity> findAll();

    CategoryEntity findByName(String name);

    boolean existsByName(String name);

}
