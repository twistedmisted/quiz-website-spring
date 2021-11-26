package com.epam.final_project.entity;

import com.epam.final_project.model.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public static RoleEntity valueOf(RoleType roleType) {
        return new RoleEntity(roleType);
    }

    private RoleEntity(RoleType roleType) {
        this.roleType = roleType;
    }

}
