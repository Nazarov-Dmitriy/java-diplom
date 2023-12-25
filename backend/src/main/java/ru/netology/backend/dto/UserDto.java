package ru.netology.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class UserDto {
    @NotEmpty
    private String login ;
    @NotEmpty
    private String password ;
}
