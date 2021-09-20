package com.epam.final_project.service;

import com.epam.final_project.entity.AnswerEntity;
import com.epam.final_project.repository.AnswersRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class AnswerService implements Serializable {

    private final AnswersRepository answersRepository;

    public AnswerService(AnswersRepository answersRepository) {
        this.answersRepository = answersRepository;
    }

    public AnswerEntity save(AnswerEntity answer) {
        return answersRepository.save(answer);
    }

    public List<String> getAllByQuestionId(long id) {
        return answersRepository.findAnswerByQuestionId(id);
    }

}
