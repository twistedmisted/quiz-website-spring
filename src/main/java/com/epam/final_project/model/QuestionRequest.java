package com.epam.final_project.model;

import com.epam.final_project.entity.VariantEntity;
import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {

    private List<VariantEntity> userAnswers;

}
