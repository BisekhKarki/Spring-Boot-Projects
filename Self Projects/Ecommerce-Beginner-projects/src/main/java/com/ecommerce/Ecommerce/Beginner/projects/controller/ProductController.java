package com.ecommerce.Ecommerce.Beginner.projects.controller;

import com.ecommerce.Ecommerce.Beginner.projects.dto.ProductDto;
import com.ecommerce.Ecommerce.Beginner.projects.model.Product;
import com.ecommerce.Ecommerce.Beginner.projects.response.Response;
import com.ecommerce.Ecommerce.Beginner.projects.service.Impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private  final ProductServiceImpl productService;

    public  ProductController(
            ProductServiceImpl productService
    ){
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(
    ) throws  Exception{
        List<Product> getProduct = productService.getAllProduct();
        if(getProduct == null){
            return ResponseEntity.status(400).body(new Response(400,"No Products found",null));

        }
        return ResponseEntity.status(200).body(new Response(200,"All Products",getProduct));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(
            @PathVariable Long productId
    ) throws  Exception{
        Product getProduct = productService.getProductById(productId);
        if(getProduct == null){
            return ResponseEntity.status(400).body(new Response(400,"No Product found",null));

        }
        return ResponseEntity.status(200).body(new Response(200,"All Products",getProduct));
    }

    @PostMapping("/create")
    public ResponseEntity<?> getProductById(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody ProductDto productDto
    ) throws  Exception{
        String token = accessToken.replace("Bearer ","");
        Product newProduct = productService.postProduct(token,productDto);
        if(newProduct == null){
            return ResponseEntity.status(400).body(new Response(400,"Failed to create Product",null));

        }
        return ResponseEntity.status(201).body(new Response(201,"Product Created Successfully",newProduct));
    }

    @PatchMapping("/{productId}/update")
    public ResponseEntity<?> updateProduct(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody ProductDto productDto,
            @PathVariable Long productId
    ) throws  Exception{
        String token = accessToken.replace("Bearer ","");
        Product updatedProduct = productService.updateProduct(token,productId,productDto);
        if(updatedProduct == null){
            return ResponseEntity.status(400).body(new Response(400,"Failed to update Product",null));

        }
        return ResponseEntity.status(201).body(new Response(201,"Product Updated Successfully",updatedProduct));
    }

    @PatchMapping("/{productId}/delete")
    public ResponseEntity<?> deleteProduct(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long productId
    ) throws  Exception{
        String token = accessToken.replace("Bearer ","");
        String deletedProduct = productService.deleteProduct(token,productId);
        if(deletedProduct.isEmpty()){
            return ResponseEntity.status(400).body(new Response(400,"Failed to delete Product",null));

        }
        return ResponseEntity.status(200).body(new Response(200,"",deletedProduct));
    }
}
