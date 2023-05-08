package com.pmso.product_management_system_original.controllers;

import com.pmso.product_management_system_original.dataaccess.dao.CategoryDao;
import com.pmso.product_management_system_original.dataaccess.entities.Category;
import com.pmso.product_management_system_original.logic.impl.ProductServiceImpl;
import com.pmso.product_management_system_original.to.ProductDto;
import com.pmso.product_management_system_original.validator.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryDao categoryDao;

    @GetMapping("/")
    public List<ProductDto> allProducts(){
        return this.productService.getProducts();
    }

    @GetMapping("/newList")
    public List<ProductDto> newProducts(){
        return this.productService.products();
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        Long categoryId = productDto.getCategoryId();
        if (categoryId != null) {
            Optional<Category> existingCategory = categoryDao.findById(categoryId);
            if (existingCategory.isPresent()) {
                productDto.setCategoryId(existingCategory.get().getId());
            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "product not added"), HttpStatus.BAD_REQUEST);
            }
        }

        // Save the product to the database
        productService.addProduct(productDto);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been added"), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable long id){
        this.productService.deleteProduct(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable long id, @ModelAttribute ProductDto updatedProduct) throws Exception {
        Long categoryId = updatedProduct.getCategoryId();
        if (categoryId != null) {
            Optional<Category> existingCategory = categoryDao.findById(categoryId);
            if (existingCategory.isPresent()) {
                updatedProduct.setCategoryId(existingCategory.get().getId());
            } else {
                return new ResponseEntity<ApiResponse>(new ApiResponse(false, "product not updated"), HttpStatus.BAD_REQUEST);
            }
        }
        this.productService.updateProduct(id, updatedProduct);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "product has been updated"), HttpStatus.OK);
    }

    @PutMapping("/update-delete/{id}")
    public void deleteUpdateProduct(@PathVariable long id) throws Exception {
        this.productService.deleteUpdateProduct(id);
    }

    @GetMapping("/count")
    public int countProducts(){
        return this.productService.counting();
    }

    @GetMapping("/getProduct/{id}")
    public ProductDto getProduct(@PathVariable Long id){
        return this.productService.getProduct(id);
    }
}
