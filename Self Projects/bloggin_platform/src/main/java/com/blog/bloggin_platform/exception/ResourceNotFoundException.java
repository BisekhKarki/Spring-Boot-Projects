package com.blog.bloggin_platform.exception;

public class ResourceNotFoundException  extends  RuntimeException{
    public  ResourceNotFoundException(String message){
        super(message);
    }
}
