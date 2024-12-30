package com.example.backend.controllers;

import com.example.backend.entities.User;
import com.example.backend.http.AppResponse;
import com.example.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        var response = userService.register(user);
        return AppResponse.success()
                .withMessage("User registered successfully")
                .withData(response)
                .withCode(HttpStatus.CREATED)
                .build();
    }
}
