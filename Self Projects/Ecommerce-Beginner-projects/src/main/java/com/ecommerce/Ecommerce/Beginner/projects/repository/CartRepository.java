package com.ecommerce.Ecommerce.Beginner.projects.repository;

import com.ecommerce.Ecommerce.Beginner.projects.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository<Cart, Long> {
}
