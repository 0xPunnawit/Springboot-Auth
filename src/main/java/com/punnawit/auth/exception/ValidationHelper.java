package com.punnawit.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidationHelper {

    public static ResponseEntity<String> handleValidationErrors(BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            result.getAllErrors().forEach(error -> {
                errorMessage.append(error.getDefaultMessage()).append(", ");
            });
            return ResponseEntity.badRequest().body(errorMessage.toString());
        } else {
            return null;
        }
    }
}
