package com.springboot.crudProject.Controller;

import com.springboot.crudProject.Model.productModel;
import com.springboot.crudProject.Model.productDTO;
import com.springboot.crudProject.Service.productService;
import com.springboot.crudProject.Utils.utilResponse;
import com.springboot.crudProject.Utils.utliSearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class productController {

    @Autowired
    private productService productService;

    @PostMapping("/create")
    public utilResponse<productDTO> createProduct(@RequestBody productModel product) {
        return productService.createProduct(product);
    }

    @PostMapping("/list")
    public ResponseEntity<utilResponse<List<productDTO>>> searchUsers(@RequestBody utliSearch searchRequest) {
        utilResponse<List<productDTO>> response = productService.getAllProducts(searchRequest.getSearch());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public utilResponse<productDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PatchMapping("/{id}/update")
    public utilResponse<productDTO> updateProduct(@PathVariable Long id, @RequestBody productModel productDetails) {
        return productService.updateProduct(id, productDetails);
    }

    @DeleteMapping("/{id}")
    public utilResponse<Void> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}