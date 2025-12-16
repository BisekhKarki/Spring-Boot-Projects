package com.ecommerce.Ecommerce.Beginner.projects.service;


import com.ecommerce.Ecommerce.Beginner.projects.dto.ProductDto;
import com.ecommerce.Ecommerce.Beginner.projects.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct() throws  Exception;
    Product getProductById(Long productId) throws  Exception;
    Product postProduct(String accessToken, ProductDto productDto) throws  Exception ;
    Product updateProduct(String accessToken, Long productId, ProductDto productDto) throws  Exception;
    String deleteProduct(String accessToken, Long productId) throws  Exception;


}
