package com.springboot.crudProject.Service;

import com.springboot.crudProject.Model.categoryModel;
import com.springboot.crudProject.Model.categoryDTO;
import com.springboot.crudProject.Repository.categoryRepo;
import com.springboot.crudProject.Utils.utilResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class categoryService {

    @Autowired
    private categoryRepo categoryRepo;

    // Convert categoryModel to categoryDTO
    private categoryDTO convertToDTO(categoryModel category) {
        return new categoryDTO(
            category.getId(),
            category.getName(),
            category.getDescription()
        );
    }

    // Create category
    public utilResponse<categoryDTO> createCategory(categoryModel category) {
        categoryModel savedCategory = categoryRepo.save(category);
        return new utilResponse<>("Category created successfully", convertToDTO(savedCategory));
    }

    // Get all categories
    public utilResponse<List<categoryDTO>> getAllCategories(String search) {
        List<categoryModel> categories;
        if (search == null || search.trim().isEmpty() || !search.matches(".*\\S.*")) {
            categories = categoryRepo.findAll();
        } else {
            String lowerSearch = search.toLowerCase();
            categories = categoryRepo.findAll().stream().filter(category ->
                    (category.getName() != null && category.getName().toLowerCase().contains(lowerSearch)) ||
                    (category.getDescription() != null && category.getDescription().toLowerCase().contains(lowerSearch))
                ).collect(Collectors.toList());
        }
        List<categoryDTO> categoryDTOs = categories.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new utilResponse<>("Category list fetched successfully", categoryDTOs);
    }

    // Get category by id
    public utilResponse<categoryDTO> getCategoryById(Long id) {
        Optional<categoryModel> category = categoryRepo.findById(id);
        if (category.isPresent()) {
            return new utilResponse<>("Category found", convertToDTO(category.get()));
        } else {
            return new utilResponse<>("Category not found", null);
        }
    }

    // Update category
    public utilResponse<categoryDTO> updateCategory(Long id, categoryModel categoryDetails) {
        Optional<categoryModel> optionalCategory = categoryRepo.findById(id);
        if (optionalCategory.isPresent()) {
            categoryModel category = optionalCategory.get();
            category.setName(categoryDetails.getName());
            category.setDescription(categoryDetails.getDescription());
            categoryModel updatedCategory = categoryRepo.save(category);
            return new utilResponse<>("Category updated successfully", convertToDTO(updatedCategory));
        } else {
            return new utilResponse<>("Category not found", null);
        }
    }

    // Delete category
    public utilResponse<Void> deleteCategory(Long id) {
        if (categoryRepo.existsById(id)) {
            categoryRepo.deleteById(id);
            return new utilResponse<>("Category deleted successfully", null);
        } else {
            return new utilResponse<>("Category not found", null);
        }
    }
}