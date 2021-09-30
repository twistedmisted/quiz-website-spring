package com.epam.final_project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "quiz", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuizEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer time;

    private String difficulty;

    private String subject;

    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private List<UsersQuizzesEntity> userQuizzes;

    @OneToMany(mappedBy = "quizId")
    private List<QuestionEntity> questions;

    public static QuizEntity createQuizEntity(String name, Integer time, String subject, String difficulty) {
        QuizEntity quiz = new QuizEntity();
        quiz.setName(name);
        quiz.setTime(time);
        quiz.setSubject(subject);
        quiz.setDifficulty(difficulty);
        return quiz;
    }

}
