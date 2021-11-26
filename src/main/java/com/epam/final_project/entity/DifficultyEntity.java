package com.epam.final_project.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "difficulty")
public class DifficultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "difficulty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizEntity> quizzes = new ArrayList<>();

    public static DifficultyEntity valueOf(String difficulty) {
        return new DifficultyEntity(difficulty);
    }

    private DifficultyEntity(String name) {
        this.name = name;
    }

}
