package com.bisekh.category.service;


import com.bisekh.category.model.Category;
import com.bisekh.category.payload.dto.SalonDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CategoryService  {
    Category saveCategory(Category category, SalonDTO salondto);
    Set<Category> getAllCategoryBySalon(Long id);
    Category getCategoryById(Long id) throws  Exception;
    void deleteCategoryById(Long id, Long salonId)  throws  Exception;

}
