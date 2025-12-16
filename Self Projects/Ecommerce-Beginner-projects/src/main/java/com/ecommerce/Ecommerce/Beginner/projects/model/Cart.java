package com.ecommerce.Ecommerce.Beginner.projects.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product item;
    @ManyToOne
    @JoinColumn(name = "userId")
    private UserModel user;
    private Integer quantity;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
