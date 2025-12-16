package com.ecommerce.Ecommerce.Beginner.projects.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDto {

    private Long Id;
    private String username;
    private String email;
    private  String password;
}
