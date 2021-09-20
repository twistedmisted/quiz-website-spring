package com.epam.final_project.model;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String login;

    private String email;

    private String accessLevel;

    public static User createUser(Long id, String login, String email, String accessLevel) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setEmail(email);
        user.setAccessLevel(accessLevel);
        return user;
    }

}
