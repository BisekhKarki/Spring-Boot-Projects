package com.ecommerce.Ecommerce.Beginner.projects.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
