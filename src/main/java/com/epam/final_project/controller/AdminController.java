package com.epam.final_project.controller;

import com.epam.final_project.model.Question;
import com.epam.final_project.model.Quiz;
import com.epam.final_project.model.User;
import com.epam.final_project.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('admin')")
public class AdminController {

    private final UserService userService;

    private final QuizService quizService;

    private final QuestionService questionService;

    @GetMapping("/home")
    public String showAdminHome() {
        return "/admin/home";
    }

    @GetMapping("/users")
    public String showAll(@RequestParam Optional<Integer> page, Model model) {
        return userService.showAll(page, model);
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(Model model, @PathVariable("id") long id) {
        return userService.getUser(model, id);
    }

    @PostMapping( "/users/edit/{id}")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        return userService.edit(user, id);
    }

    @GetMapping( "/users/block/{id}")
    public String blockUser(@PathVariable("id") long id) {
        userService.updateAccessLevel(id, "BANNED");
        return "redirect:/admin/users";
    }

    @GetMapping( "/users/unblock/{id}")
    public String unblockUser(@PathVariable("id") long id) {
        userService.updateAccessLevel(id, "USER");
        return "redirect:/admin/users";
    }

    @GetMapping( "/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/quizzes")
    public String showQuizzes(@RequestParam Optional<Integer> page, Model model) {
        return quizService.showAll(page, model);
    }

    @GetMapping("/quizzes/edit/{id}")
    public String showEditQuizForm(Model model, @PathVariable("id") long id) {
        return quizService.getQuiz(model, id);
    }

    @PostMapping( "/quizzes/edit/{id}")
    public String editQuiz(@ModelAttribute("quiz") Quiz quiz, @PathVariable("id") long id) {
        return quizService.edit(quiz, id);
    }

    @GetMapping("/quizzes/delete/{id}")
    public String deleteQuiz(@PathVariable("id") long id) {
        quizService.deleteById(id);
        return "redirect:/admin/quizzes";
    }

    @GetMapping("/quizzes/add")
    public String showAddQuizForm(Model model) {
        model.addAttribute("quiz", new Quiz());
        return "/admin/add-quiz";
    }

    @PostMapping("/quizzes/add")
    public String addQuiz(@ModelAttribute("quiz") @Valid Quiz quiz, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/quizzes?error";
        }
        return quizService.add(quiz);
    }

    @GetMapping("/quizzes/{id}/questions")
    public String showQuestions(@PathVariable("id") long id, Model model) {
        return questionService.showAllByQuizId(id, model);
    }

    @GetMapping("/quizzes/{id}/questions/add")
     public String showAddQuestionForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("quizId", id);
        model.addAttribute("question", new Question());
        return "/admin/add-question";
    }

    @PostMapping("/quizzes/{id}/questions/add")
    public String addQuestion(@PathVariable("id") long id, @ModelAttribute("question") Question question) {
        return questionService.addQuestion(id, question);
    }

    @GetMapping("/quizzes/{id}/questions/delete/{index}")
    public String deleteQuestion(@PathVariable("id") long id, @PathVariable("index") long index) {
        questionService.deleteById(index);
        return "redirect:/admin/quizzes/{id}/questions";
    }

    public AdminController(UserService userService,
                           QuizService quizService,
                           QuestionService questionService) {
        this.userService = userService;
        this.quizService = quizService;
        this.questionService = questionService;
    }

}
