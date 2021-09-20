package com.epam.final_project.repository;

import com.epam.final_project.entity.AnswerEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswersRepository extends CrudRepository<AnswerEntity, Long> {

    AnswerEntity save(AnswerEntity answer);

    @Query(value = "SELECT answer FROM answers WHERE question_id = :id", nativeQuery = true)
    List<String> findAnswerByQuestionId(@Param("id") long id);

}
