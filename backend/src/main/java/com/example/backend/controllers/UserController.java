package com.example.backend.controllers;

import com.example.backend.dto.CreateUserDTO;
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
    public ResponseEntity<?> register(@RequestBody @Valid CreateUserDTO createUserDTO) {
        var result = userService.register(createUserDTO);
        return AppResponse.success()
                .withMessage("User registered successfully")
                .withData(result)
                .withCode(HttpStatus.CREATED)
                .build();
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(name = "userId", required = true) int id,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size
    ) {
        int pageIndex = page > 0 ? page - 1 : 0;
        var result = userService.getAll(id, email, pageIndex, size);
        return AppResponse.success()
                .withMessage("Users found successfully")
                .withData(result.getContent())
                .withPagination(result)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        var result = userService.getOne(id);
        return AppResponse.success()
                .withMessage("User found successfully")
                .withData(result)
                .build();
    }
}
