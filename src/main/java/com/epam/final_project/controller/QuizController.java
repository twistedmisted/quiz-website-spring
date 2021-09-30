package com.epam.final_project.controller;

import com.epam.final_project.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequestMapping("/app/quiz")
@Controller
@Slf4j
public class QuizController {

    private final UsersQuizzesService usersQuizzesService;

    private final QuizService quizService;

    private final QuestionService questionService;

    private final UserService userService;

    private final AnswerService answerService;

    @GetMapping("/{id}")
    public String showQuiz(@PathVariable long id, Model model, Principal principal) {
        return quizService.showQuiz(id, model, principal);
    }

    @GetMapping("/{id}/question/{index}")
    public String showQuestion(@PathVariable long id,
                               @PathVariable int index,
                               HttpServletRequest request,
                               Model model,
                               Principal principal) {
        return questionService.showByQuizId(id, index, request, model, principal);
    }

    @PostMapping("/{id}/question/{index}")
    public String nextQuestion(@PathVariable long id,
                               @PathVariable int index,
                               @RequestParam(value = "answer", required = false, defaultValue = "NULL") String answers,
                               HttpServletRequest request,
                               Model model,
                               Principal principal) {
        return questionService.showNextQuestion(id, index, answers, request, model, principal);
    }

    public QuizController(UsersQuizzesService usersQuizzesService,
                          QuizService quizService,
                          QuestionService questionService,
                          UserService userService, AnswerService answerService) {
        this.usersQuizzesService = usersQuizzesService;
        this.quizService = quizService;
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
    }

}
