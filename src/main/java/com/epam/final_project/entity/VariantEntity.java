package com.epam.final_project.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "variants")
@Setter
@Getter
@EqualsAndHashCode(exclude = {"question"})
@NoArgsConstructor
@AllArgsConstructor
public class VariantEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variant")
    private String option;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    public String toString() {
        return option;
    }

}
