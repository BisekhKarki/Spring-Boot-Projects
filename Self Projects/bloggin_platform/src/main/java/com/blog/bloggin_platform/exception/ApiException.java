package com.blog.bloggin_platform.exception;

public class ApiException extends  RuntimeException {
    public ApiException(String message){
        super(message);
    }
}
