package com.ecom.product.service;

import com.ecom.product.model.Product;
import org.springframework.stereotype.Service;
import com.ecom.product.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public List<Product> getProductByCategory(Long categoryId){
        return  productRepository.findByCategoryId(categoryId);
    }


}
