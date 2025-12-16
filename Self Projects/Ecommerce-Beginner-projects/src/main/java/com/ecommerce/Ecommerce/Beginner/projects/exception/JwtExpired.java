package com.ecommerce.Ecommerce.Beginner.projects.exception;

public class JwtExpired extends RuntimeException {
    public JwtExpired(String message) {
        super(message);
    }
}
