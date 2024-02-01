package ru.netology.backend.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ErrorMessageLogin {
    private int id;
    private String[] email;
    private String[] password;
}
