package com.ecommerce.Ecommerce.Beginner.projects.dto;

import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDto {
    private Long id;

    private String name;
    private String description;
    private Long price;
    private String imageUrl;
    private UserModel owner;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
