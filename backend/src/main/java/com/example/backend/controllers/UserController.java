package com.example.backend.controllers;

import com.example.backend.entities.User;
import com.example.backend.http.AppResponse;
import com.example.backend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {
        var result = userService.register(user);
        return AppResponse.success()
                .withMessage("User registered successfully")
                .withData(result)
                .withCode(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        var result = userService.getAll(email, page, size);
        return AppResponse.success()
                .withMessage("Users found successfully")
                .withData(result.getContent())
                .withPagination(result)
                .build();
    }
}
