package com.example.backend.controllers;

import com.example.backend.http.AppResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler class that handles all exceptions thrown by the controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method intercepts {@link MethodArgumentNotValidException}, which occurs when a request
     * body or request parameter fails validation. It collects field errors and returns a
     * standardized error response with the validation messages.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return AppResponse.error()
                .withMessage("Invalid fields in the request")
                .withCode(HttpStatus.BAD_REQUEST)
                .withErrors(errors)
                .build();
    }

    /**
     * This method acts as a fallback to handle any unhandled exceptions globally. It ensures that
     * when an unexpected error occurs, a structured error message is returned along with a 400 BAD_REQUEST
     * HTTP status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
        if (ex.getClass().getSimpleName().contains("NotFoundException")) {
            return AppResponse.error()
                    .withMessage(ex.getMessage())
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
        return AppResponse.error()
                .withMessage(ex.getMessage())
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
    }
}
