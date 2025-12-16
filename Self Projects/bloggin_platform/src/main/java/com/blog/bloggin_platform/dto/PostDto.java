package com.blog.bloggin_platform.dto;


import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class PostDto {

    private String title;
    private  String content;

}
