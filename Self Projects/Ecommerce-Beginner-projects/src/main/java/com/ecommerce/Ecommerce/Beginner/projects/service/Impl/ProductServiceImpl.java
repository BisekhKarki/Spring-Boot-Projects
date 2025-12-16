package com.ecommerce.Ecommerce.Beginner.projects.service.Impl;

import com.ecommerce.Ecommerce.Beginner.projects.components.JWT;
import com.ecommerce.Ecommerce.Beginner.projects.dto.ProductDto;
import com.ecommerce.Ecommerce.Beginner.projects.exception.ApiException;
import com.ecommerce.Ecommerce.Beginner.projects.exception.JwtException;
import com.ecommerce.Ecommerce.Beginner.projects.exception.JwtExpired;
import com.ecommerce.Ecommerce.Beginner.projects.model.Product;
import com.ecommerce.Ecommerce.Beginner.projects.model.UserModel;
import com.ecommerce.Ecommerce.Beginner.projects.repository.ProductRepository;
import com.ecommerce.Ecommerce.Beginner.projects.repository.UserRepository;
import com.ecommerce.Ecommerce.Beginner.projects.service.ProductService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final JWT jwt;
    private  final UserRepository userRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            JWT jwt,
            UserRepository userRepository
    ){
        this.jwt = jwt;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<Product> getAllProduct() throws Exception {
        try{
            return  productRepository.findAll();
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e){
            throw new ApiException("Internal Server error");
        }

    }

    @Override
    public Product getProductById(Long productId) throws Exception {
        try{
            return  productRepository.findById(productId).orElseThrow(()-> new ApiException("No product found with id: "+productId));
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e){
            throw new ApiException("Internal Server error");
        }
    }

    @Override
    public Product postProduct(String accessToken, ProductDto productDto) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId  = token.get("id",Long.class);
            UserModel user = userRepository.findById(userId).orElseThrow(() -> new ApiException("Unauthorized user"));
            UserModel userDetails = new UserModel();
            userDetails.setId(userId);
            userDetails.setEmail(user.getEmail());
            userDetails.setUsername(user.getUsername());
            userDetails.setCreatedAt(user.getCreatedAt());
            userDetails.setUpdatedAt(user.getUpdatedAt());
            Product newProduct = new Product();
            newProduct.setName(productDto.getName());
            newProduct.setDescription(productDto.getDescription());
            newProduct.setOwner(userDetails);
            newProduct.setPrice(productDto.getPrice());
            newProduct.setImageUrl(Collections.singletonList(productDto.getImageUrl()));
            newProduct.setCreatedAt(LocalDateTime.now());
            newProduct.setUpdatedAt(LocalDateTime.now());
            return  productRepository.save(newProduct);
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e){
            throw new ApiException("Internal Server error");
        }
    }

    @Override
    public Product updateProduct(String accessToken, Long productId, ProductDto productDto) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId  = token.get("id",Long.class);

            Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new ApiException("No Product Found..."));
            if(!existingProduct.getOwner().getId().equals(userId)){
                throw new JwtException("Unauthorized user");
            }
            existingProduct.setName(productDto.getName());
            existingProduct.setDescription(productDto.getDescription());
            existingProduct.setImageUrl(Collections.singletonList(productDto.getImageUrl()));
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setUpdatedAt(LocalDateTime.now());
           return productRepository.save(existingProduct);
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e){
            throw new ApiException("Internal Server error");
        }
    }

    @Override
    public String deleteProduct(String accessToken, Long productId) throws Exception {
        try{
            Claims token = jwt.decodeToken(accessToken);
            Long userId  = token.get("id",Long.class);
            Product existingProduct = productRepository.findById(productId).orElseThrow(()-> new ApiException("No Product Found..."));
            if(!existingProduct.getOwner().getId().equals(userId)){
                throw new JwtException("Unauthorized user");
            }
            productRepository.deleteById(productId);
            return "Product Deleted Successfully";
        }catch ( ExpiredJwtException expiredJwtException){
            throw new JwtExpired("Token expired");
        } catch (Exception e){
            throw new ApiException("Internal Server error");
        }
    }
}
