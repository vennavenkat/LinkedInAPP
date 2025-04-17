package com.venkat.linkedin.user_service.controller;

import com.venkat.linkedin.user_service.dto.LoginRequestDto;
import com.venkat.linkedin.user_service.dto.SignupRequestDto;
import com.venkat.linkedin.user_service.dto.UserDto;
import com.venkat.linkedin.user_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto){

        UserDto userDto = authService.signUp(signupRequestDto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> signUp(@RequestBody LoginRequestDto loginRequestDto){

        String token = authService.login(loginRequestDto);

        return  ResponseEntity.ok(token);

    }
}
