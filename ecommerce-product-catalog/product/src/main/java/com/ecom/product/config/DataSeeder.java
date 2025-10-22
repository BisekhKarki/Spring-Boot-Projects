package com.ecom.product.config;


import com.ecom.product.model.Category;
import com.ecom.product.model.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.ecom.product.repository.CategoryRepository;
import com.ecom.product.repository.ProductRepository;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public DataSeeder(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws  Exception{
//        Clear all existing data
        productRepository.deleteAll();
        categoryRepository.deleteAll();

//        Create Categories
        Category electronics = new Category();
        electronics.setName("Electronics");

        Category clothing = new Category();
        clothing.setName("Clothing");

        Category home = new Category();
        home.setName("Home and Kitchen");


        categoryRepository.saveAll(Arrays.asList(electronics,clothing,home));




        //        Create Products

        Product phone = new Product();
        phone.setName("Smart Phone");
        phone.setDescription("Latest model smartphone with amazing features");
        phone.setImageUrl("https://placehold.co/600x400");
        phone.setPrice(699.99);
        phone.setCategory(electronics);


        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setDescription("High-performance laptop suitable for work and gaming");
        laptop.setImageUrl("https://placehold.co/600x400");
        laptop.setPrice(1299.99);
        laptop.setCategory(electronics);

        Product jacket = new Product();
        jacket.setName("Winter Jacket");
        jacket.setDescription("Warm and stylish jacket for cold weather");
        jacket.setImageUrl("https://placehold.co/600x400");
        jacket.setPrice(129.99);
        jacket.setCategory(clothing);


        Product blender = new Product();
        blender.setName("Blender");
        blender.setDescription("Powerful blender for smoothies and cooking needs");
        blender.setImageUrl("https://placehold.co/600x400");
        blender.setPrice(89.99);
        blender.setCategory(home);

        productRepository.saveAll(Arrays.asList(phone,laptop,jacket,blender));


    }
}
