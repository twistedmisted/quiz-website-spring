package com.epam.final_project.repository;

import com.epam.final_project.entity.QuizEntity;
import com.epam.final_project.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends CrudRepository<QuizEntity, Long> {

    QuizEntity save(QuizEntity quiz);

    Optional<QuizEntity> findById(long id);

    List<QuizEntity> findAll();

    List<QuizEntity> findAllByOrderByIdDesc();

    List<QuizEntity> findAllBySubject(String subject);

    List<QuizEntity> findAllByOrderByName();

    Page<QuizEntity> findAll(Pageable pageable);

    @Query(value = "SELECT quiz.* FROM quiz, questions_quiz " +
            "WHERE quiz.id=questions_quiz.quiz_id " +
            "GROUP BY questions_quiz.quiz_id " +
            "ORDER BY COUNT(questions_quiz.quiz_id)",
            nativeQuery = true)
    List<QuizEntity> findAllSortedByQuestions();

    @Query(value = "SELECT DISTINCT subject FROM quiz", nativeQuery = true)
    List<String> findSubjects();

    void deleteById(long id);

}
