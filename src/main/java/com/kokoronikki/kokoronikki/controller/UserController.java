package com.kokoronikki.kokoronikki.controller;

import com.kokoronikki.kokoronikki.controller.dto.LoginRequestDto;
import com.kokoronikki.kokoronikki.controller.dto.LoginResponseDto;
import com.kokoronikki.kokoronikki.controller.dto.SignupDto;
import com.kokoronikki.kokoronikki.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        String token = userService.login(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token);
        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupDto signupDto) {
        userService.registerUser(signupDto);
        return ResponseEntity.ok("회원가입 성공");
    }
}
