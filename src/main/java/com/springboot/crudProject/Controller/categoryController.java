package com.springboot.crudProject.Controller;

import com.springboot.crudProject.Model.categoryModel;
import com.springboot.crudProject.Model.categoryDTO;
import com.springboot.crudProject.Service.categoryService;
import com.springboot.crudProject.Utils.utilResponse;
import com.springboot.crudProject.Utils.utliSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/category")
public class categoryController {

    @Autowired
    private categoryService categoryService;

    @PostMapping("/create")
    public utilResponse<categoryDTO> createCategory(@RequestBody categoryModel category) {
        return categoryService.createCategory(category);
    }

    @PostMapping("/list")
    public ResponseEntity<utilResponse<List<categoryDTO>>> searchUsers(@RequestBody utliSearch searchRequest) {
        utilResponse<List<categoryDTO>> response = categoryService.getAllCategories(searchRequest.getSearch());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public utilResponse<categoryDTO> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PatchMapping("/{id}/update")
    public utilResponse<categoryDTO> updateCategory(@PathVariable Long id, @RequestBody categoryModel categoryDetails) {
        return categoryService.updateCategory(id, categoryDetails);
    }

    @DeleteMapping("/{id}")
    public utilResponse<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}