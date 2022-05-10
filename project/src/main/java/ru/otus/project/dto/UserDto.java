package ru.otus.project.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 50, message = "Name must have size between 2 and 50")
    private String name;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 5, max = 150, message = "Password must have size between 5 and 150")
    private String password;
}
