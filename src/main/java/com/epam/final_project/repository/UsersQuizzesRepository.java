package com.epam.final_project.repository;

import com.epam.final_project.entity.UsersQuizzesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersQuizzesRepository extends CrudRepository<UsersQuizzesEntity, Long> {

    UsersQuizzesEntity save(UsersQuizzesEntity entity);

    UsersQuizzesEntity findByUserIdAndQuizId(long userId, long quizId);

    List<UsersQuizzesEntity> findAllByUserId(long id);

    List<UsersQuizzesEntity> findAllByUserLogin(String login);

    Optional<UsersQuizzesEntity> findByUserLoginAndQuizId(String login, long id);



}
