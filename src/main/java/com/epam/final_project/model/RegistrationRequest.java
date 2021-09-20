package com.epam.final_project.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Login should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String login;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 30, message = "Password should be between 8 and 30 characters")
    private String password;

    @NotEmpty(message = "Matching password should not be empty")
    @Size(min = 8, max = 30, message = "Password should be between 8 and 30 characters")
    private String matchingPassword;

}
