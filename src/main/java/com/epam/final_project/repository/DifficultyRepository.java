package com.epam.final_project.repository;

import com.epam.final_project.entity.DifficultyEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DifficultyRepository extends CrudRepository<DifficultyEntity, Long> {

    DifficultyEntity findByName(String name);

    boolean existsByName(String name);

}
