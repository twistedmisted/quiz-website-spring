package com.epam.final_project.repository;

import com.epam.final_project.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {

    QuestionEntity save(QuestionEntity question);

    List<QuestionEntity> findAllByQuizIdId(long id);

    Page<QuestionEntity> findAllByQuizIdId(long id, Pageable pageable);

    long countAllByQuizIdId(long id);

    void deleteById(long id);

}
