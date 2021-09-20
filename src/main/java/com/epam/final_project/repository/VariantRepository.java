package com.epam.final_project.repository;

import com.epam.final_project.entity.VariantEntity;
import org.springframework.data.repository.CrudRepository;

public interface VariantRepository extends CrudRepository<VariantEntity, Long> {

    VariantEntity save(VariantEntity variant);

}
