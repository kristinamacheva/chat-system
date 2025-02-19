package com.example.backend.http;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for building standardized HTTP responses.
 */
public class AppResponse {

    private static Map<String, Object> response;

    /**
     * Creates a success response with a default status and code.
     */
    public static AppResponse success() {
        response = new HashMap<>();
        response.put("status", "success");
        response.put("code", HttpStatus.OK.value());
        return new AppResponse();
    }

    /**
     * Creates an error response with a default status and code.
     */
    public static AppResponse error() {
        response = new HashMap<>();
        response.put("status", "error");
        response.put("code", HttpStatus.BAD_REQUEST.value());
        return new AppResponse();
    }

    /**
     * Updates the response code.
     * @param code the HTTP status code to set
     */
    public AppResponse withCode(HttpStatus code) {
        response.put("code", code.value());
        return this;
    }

    /**
     * Adds a custom message to the response.
     * @param message the message to include in the response
     */
    public AppResponse withMessage(String message) {
        response.put("message", message);
        return this;
    }

    /**
     * Adds data to the response.
     * @param data the data to include in the response
     */
    public AppResponse withData(Object data) {
        response.put("data", data);
        return this;
    }

    /**
     * Wraps the provided data in an array and adds it to the response.
     * @param data the data to include in the response as an array
     */
    public AppResponse withDataAsArray(Object data) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(data);
        return this.withData(list);
    }

    /**
     * Adds pagination metadata to the response.
     * @param page the {@link Page} object that contains the paginated data and metadata.
     */
    public AppResponse withPagination(Page<?> page) {
        response.put("totalPages", page.getTotalPages());
        response.put("currentPage", page.getNumber() + 1);
        response.put("pageSize", page.getSize());
        return this;
    }

    /**
     * Adds errors to the response.
     * @param errors the errors to include in the response
     */
    public AppResponse withErrors(Map<String, String> errors) {
        response.put("errors", errors);
        return this;
    }

    /**
     * Builds the response and returns it as a {@link ResponseEntity}.
     */
    public ResponseEntity<Object> build() {
        int code = (int) response.get("code");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(code));
    }
}
