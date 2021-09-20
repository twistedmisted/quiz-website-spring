package com.epam.final_project.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users_quizzes", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersQuizzesEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = false)
    @JsonBackReference
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private QuizEntity quiz;

    private Integer score = 0;

    public static UsersQuizzesEntity create(UserEntity user, QuizEntity quiz) {
        UsersQuizzesEntity entity = new UsersQuizzesEntity();
        entity.setUser(user);
        entity.setQuiz(quiz);
        return entity;
    }
}
