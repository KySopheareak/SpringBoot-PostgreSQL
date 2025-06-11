package com.springboot.crudProject.Service;

import com.springboot.crudProject.Model.productModel;
import com.springboot.crudProject.Model.productDTO;
import com.springboot.crudProject.Model.categoryDTO;
import com.springboot.crudProject.Model.categoryModel;
import com.springboot.crudProject.Repository.productRepo;
import com.springboot.crudProject.Repository.categoryRepo;
import com.springboot.crudProject.Utils.utilResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class productService {

    @Autowired
    private productRepo productRepo;

    @Autowired
    private categoryRepo categoryRepo;

    // Convert productModel to productDTO
    private productDTO convertToDTO(productModel product) {
        return new productDTO(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getCategory() != null
                ? new categoryDTO(
                    product.getCategory().getId(),
                    product.getCategory().getName(),
                    product.getCategory().getDescription()
                )
                : null
        );
    }

    // Create product
    public utilResponse<productDTO> createProduct(productModel product) {
        // Ensure category exists if set
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Optional<categoryModel> category = categoryRepo.findById(product.getCategory().getId());
            category.ifPresent(product::setCategory);
        }
        productModel savedProduct = productRepo.save(product);
        return new utilResponse<>("Product created successfully", convertToDTO(savedProduct));
    }

    // Get all products
    public utilResponse<List<productDTO>> getAllProducts(String search) {
        List<productModel> products;
        if (search == null || search.trim().isEmpty() || !search.matches(".*\\S.*")) {
            products = productRepo.findAll();
        } else {
            String lowerSearch = search.toLowerCase();
            products = productRepo.findAll().stream().filter(product ->
                    (product.getName() != null && product.getName().toLowerCase().contains(lowerSearch)) ||
                    (product.getDescription() != null && product.getDescription().toLowerCase().contains(lowerSearch))
                ).collect(Collectors.toList());
        }
        List<productDTO> productDTOs = products.stream().map(this::convertToDTO).collect(Collectors.toList());
        return new utilResponse<>("Product list fetched successfully", productDTOs);
    }

    // Get product by id
    public utilResponse<productDTO> getProductById(Long id) {
        Optional<productModel> product = productRepo.findById(id);
        if (product.isPresent()) {
            return new utilResponse<>("Product found", convertToDTO(product.get()));
        } else {
            return new utilResponse<>("Product not found", null);
        }
    }

    // Update product
    public utilResponse<productDTO> updateProduct(Long id, productModel productDetails) {
        Optional<productModel> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isPresent()) {
            productModel product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            if (productDetails.getCategory() != null && productDetails.getCategory().getId() != null) {
                Optional<categoryModel> category = categoryRepo.findById(productDetails.getCategory().getId());
                category.ifPresent(product::setCategory);
            }
            productModel updatedProduct = productRepo.save(product);
            return new utilResponse<>("Product updated successfully", convertToDTO(updatedProduct));
        } else {
            return new utilResponse<>("Product not found", null);
        }
    }

    // Delete product
    public utilResponse<Void> deleteProduct(Long id) {
        if (productRepo.existsById(id)) {
            productRepo.deleteById(id);
            return new utilResponse<>("Product deleted successfully", null);
        } else {
            return new utilResponse<>("Product not found", null);
        }
    }
}