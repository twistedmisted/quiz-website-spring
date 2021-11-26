package com.epam.final_project.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class Quiz {

    private Long id;

    @NotNull(message = "Login should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @Min(value = 1, message = "Time must be greater than 0")
    private Integer time;

    @NotNull(message = "Difficulty should not be empty")
    private String difficulty;

    @NotNull(message = "Category should not be empty")
    @Size(min = 2, max = 30, message = "Subject should be between 2 and 30 characters")
    private String category;

    public static Quiz createQuiz(Long id, String name, int time, String difficulty, String subject) {
        Quiz quiz = new Quiz();
        quiz.setId(id);
        quiz.setName(name);
        quiz.setTime(time);
        quiz.setDifficulty(difficulty);
        quiz.setCategory(subject);
        return quiz;
    }

}
