package com.ecommerce.Ecommerce.Beginner.projects.dto;

import com.ecommerce.Ecommerce.Beginner.projects.model.Product;
import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CartDto {
    private Long id;
    private Product item;
    private UserModel user;
    private Integer quantity;
    private Integer totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
