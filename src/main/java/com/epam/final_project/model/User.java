package com.epam.final_project.model;

import com.epam.final_project.entity.UserEntity;
import lombok.Data;

@Data
public class User {

    private Long id;

    private String login;

    private String email;

    private String role;

    public static User createUser(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setLogin(entity.getLogin());
        user.setEmail(entity.getEmail());
        user.setRole(String.valueOf(entity.getRole().getRoleType()));
        return user;
    }

}
