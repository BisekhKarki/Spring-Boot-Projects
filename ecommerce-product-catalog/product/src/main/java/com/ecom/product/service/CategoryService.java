package com.ecom.product.service;

import com.ecom.product.repository.CategoryRepository;
import com.ecom.product.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
       return categoryRepository.findAll();
    }
}
