package com.epam.final_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class BannedController {

    @GetMapping("/banned")
    public String showBannedPage() {
        return "/app/banned";
    }

}
