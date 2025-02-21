package com.kokoronikki.kokoronikki.controller.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDto {

    @NotEmpty(message = "username is default")
    private String username;
    @NotEmpty(message = "password is default")
    private String password;
    @NotEmpty(message = "nickname is default")
    private String nickname;
}
