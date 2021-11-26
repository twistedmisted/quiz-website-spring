package com.epam.final_project.entity;

import lombok.AccessLevel;
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
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuizEntity> quizzes = new ArrayList<>();

    public static CategoryEntity valueOf(String category) {
        return new CategoryEntity(category);
    }

    private CategoryEntity(String name) {
        this.name = name;
    }

}
