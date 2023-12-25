package ru.netology.backend.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class User {
    private long id;
    private String login;
    private String password;
    private String role;
}
