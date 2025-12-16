package com.bisekh.Task.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
public class UserDto {

    private int id;
    private  String name;
    private String email;
}
