package com.epam.final_project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "questions")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prompt;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerEntity> answers;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<VariantEntity> variants;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private QuizEntity quizId;

}
