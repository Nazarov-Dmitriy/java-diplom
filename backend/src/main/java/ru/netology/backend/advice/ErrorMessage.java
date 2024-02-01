package ru.netology.backend.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ErrorMessage {
    private int id;
    private String description;
}
