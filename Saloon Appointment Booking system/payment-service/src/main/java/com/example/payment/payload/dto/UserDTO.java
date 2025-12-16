package com.example.payment.payload.dto;

import lombok.Getter;
import lombok.Setter;

//@Data
@Setter
@Getter
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
}
