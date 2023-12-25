package ru.netology.backend.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private int status;
    private String message;
    private Date date;
    private String path;
}
