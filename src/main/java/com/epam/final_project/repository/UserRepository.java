package com.epam.final_project.repository;

import com.epam.final_project.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Modifying
    @Transactional
    @Query(value = "UPDATE user SET access_level=:accessLevel WHERE id=:id", nativeQuery = true)
    void updateAccessLevel(@Param("id") long id, @Param("accessLevel") String accessLevel);

}
