package com.bisekh.category.controller;

import com.bisekh.category.model.Category;
import com.bisekh.category.payload.dto.SalonDTO;
import com.bisekh.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor


public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody  Category category){
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        Category categories = categoryService.saveCategory(category, salonDTO);
        return  ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable  Long id) throws Exception {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        categoryService.deleteCategoryById(id, salonDTO.getId());
        return  ResponseEntity.ok("Category Deleted Successfully!!!");
    }


}
