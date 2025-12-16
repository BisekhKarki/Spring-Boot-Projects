package com.blog.bloggin_platform.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class UserDto {
    private String email;
    private String password;
}
