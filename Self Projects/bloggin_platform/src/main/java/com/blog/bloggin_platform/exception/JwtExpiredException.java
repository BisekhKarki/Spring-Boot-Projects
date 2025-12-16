package com.blog.bloggin_platform.exception;


public class JwtExpiredException extends RuntimeException {
    public JwtExpiredException(String message){
        super(message);
    }
}
