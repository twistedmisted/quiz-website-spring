package com.epam.final_project.controller;

import com.epam.final_project.model.RegistrationRequest;
import com.epam.final_project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@Slf4j
public class RegistrationController {

    private static final String REGISTRATION_PAGE = "registration";

    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return REGISTRATION_PAGE;
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") @Valid RegistrationRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/registration";
        }
        return userService.createUser(request);
    }

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }
}
