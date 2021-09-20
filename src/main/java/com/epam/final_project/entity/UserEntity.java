package com.epam.final_project.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String login;

    private String password;

    private String accessLevel;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private List<UsersQuizzesEntity> userQuizzes;

    public static UserEntity createUserEntity(String email, String login, String password) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(password);
        user.setAccessLevel("user");
        return user;
    }

}
