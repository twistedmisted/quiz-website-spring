package com.epam.final_project.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "answers")
@Setter
@Getter
@EqualsAndHashCode(exclude = {"question"})
@NoArgsConstructor
@AllArgsConstructor
public class AnswerEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    public String toString() {
        return String.valueOf(answer);
    }
}
