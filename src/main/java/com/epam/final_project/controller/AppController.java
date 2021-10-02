package com.epam.final_project.controller;

import com.epam.final_project.service.QuizService;
import com.epam.final_project.service.UserService;
import com.epam.final_project.service.UsersQuizzesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequestMapping("/app")
@Slf4j
public class AppController {

    private final QuizService quizService;

    private final UserService userService;

    private final UsersQuizzesService usersQuizzesService;

    @GetMapping("/home")
    public String showHome(Model model, Principal principal) {
        userService.showHome(model, principal);
        return "/app/home";
    }

    @GetMapping("/all-quizzes")
    public String showQuizzes(@RequestParam(defaultValue = "name") String sortBy,
                              Model model) {
        quizService.showAllQuizzes(sortBy, model);
        return "/app/all-quizzes";
    }

    @GetMapping("/my-quizzes")
    public String showQuizzes(Model model, Principal principal) {
        usersQuizzesService.showUserQuizzes(model, principal);
        return "/app/user-quizzes";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "/app/about";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        userService.showProfile(model, principal);
        return "/app/profile";
    }

    public AppController(QuizService quizService, UserService userService, UsersQuizzesService usersQuizzesService) {
        this.quizService = quizService;
        this.userService = userService;
        this.usersQuizzesService = usersQuizzesService;
    }

}
