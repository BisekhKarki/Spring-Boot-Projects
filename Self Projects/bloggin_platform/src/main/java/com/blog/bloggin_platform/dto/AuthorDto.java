package com.blog.bloggin_platform.dto;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class AuthorDto {
    private  String email;
    private String username;
    private  Long id;
}
