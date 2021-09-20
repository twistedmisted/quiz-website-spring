package com.epam.final_project.model;

import lombok.Data;

import java.util.List;

@Data
public class Question {

    private String prompt;

    private List<String> answers;

    private List<String> variants;

}
