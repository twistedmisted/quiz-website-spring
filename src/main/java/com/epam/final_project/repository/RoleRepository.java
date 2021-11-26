package com.epam.final_project.repository;

import com.epam.final_project.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByUserId(Long id);

    RoleEntity save(RoleEntity role);

}
