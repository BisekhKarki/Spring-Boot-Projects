package com.bisekh.Task.model;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private  String name;
    @Column(nullable = false)
    private String email;

}
