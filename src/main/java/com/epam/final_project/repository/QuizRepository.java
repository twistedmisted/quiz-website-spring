package com.epam.final_project.repository;

import com.epam.final_project.entity.QuizEntity;
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

    List<QuizEntity> findAllByCategoryName(String category);

    List<QuizEntity> findAllByOrderByName();

    Page<QuizEntity> findAll(Pageable pageable);

    @Query(value = "SELECT quizzes.* FROM quizzes, questions " +
            "WHERE quizzes.id=questions.quiz_id " +
            "GROUP BY questions.quiz_id " +
            "ORDER BY COUNT(questions.quiz_id)",
            nativeQuery = true)
    List<QuizEntity> findAllSortedByQuestions();

    void deleteById(long id);

}
