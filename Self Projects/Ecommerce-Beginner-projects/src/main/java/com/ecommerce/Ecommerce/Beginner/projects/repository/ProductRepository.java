package com.ecommerce.Ecommerce.Beginner.projects.repository;

import com.ecommerce.Ecommerce.Beginner.projects.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
