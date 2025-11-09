package com.bisekh.category.service.impl;

import com.bisekh.category.model.Category;
import com.bisekh.category.payload.dto.SalonDTO;
import com.bisekh.category.repository.CategoryRepository;
import com.bisekh.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl  implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salondto) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salondto.getId());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoryBySalon(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws  Exception {
        Category category = categoryRepository.findById(id).orElse(null);

        if(category == null){
            throw  new Exception("Category Do not exists with the given Id "  + id);
        }

        return category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws  Exception {
        Category category = getCategoryById(id);
        if(!category.getSalonId().equals(salonId)){
            throw new Exception("You dont have permission to delete the category");
        }
        categoryRepository.deleteById(id);

    }
}
