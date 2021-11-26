package com.epam.final_project.repository;

import com.epam.final_project.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findById(long id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByLogin(String login);

    List<UserEntity> findAll();

    UserEntity save(UserEntity user);

    Page<UserEntity> findAll(Pageable pageable);

    void deleteById(long id);

}
