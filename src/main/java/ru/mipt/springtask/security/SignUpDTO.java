package ru.mipt.springtask.security;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpDTO {
    @NotBlank
    @Size(min = 3, max = 15)
    private String userName;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}

