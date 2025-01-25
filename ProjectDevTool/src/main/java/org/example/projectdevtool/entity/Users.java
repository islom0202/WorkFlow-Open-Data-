package org.example.projectdevtool.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table
@Data
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String login;

    @Column
    private String password;

    @Column
    private LocalDateTime createdAt;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role{
        DIRECTOR,
        PM,
        EMPLOYEE
    }

    @Column
    private boolean googleAccountLinked = false;

    @Column
    private String googleAccessToken;

    @Column
    private String googleRefreshToken;
}
