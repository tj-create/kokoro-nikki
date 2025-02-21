package com.kokoronikki.kokoronikki.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;
}
