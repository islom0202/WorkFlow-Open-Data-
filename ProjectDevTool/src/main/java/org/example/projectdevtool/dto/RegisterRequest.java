package org.example.projectdevtool.dto;


import lombok.Data;

@Data
public class RegisterRequest{

    private String email;
    private String login;
    private String password;
}
