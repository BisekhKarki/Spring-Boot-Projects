package com.ecommerce.Ecommerce.Beginner.projects.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Long price;
    private List<String> imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private UserModel owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
