package com.ecommerce.Ecommerce.Beginner.projects.repository;

import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
}
