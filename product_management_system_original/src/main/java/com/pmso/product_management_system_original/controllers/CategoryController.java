package com.pmso.product_management_system_original.controllers;

import com.pmso.product_management_system_original.logic.impl.CategoryServiceImpl;
import com.pmso.product_management_system_original.to.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("/")
    public List<CategoryDto> allCategories(){
        return this.categoryService.allCategories();
    }

    @GetMapping("/newList")
    @CrossOrigin("*")
    public List<CategoryDto> newCategories(@RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
                                           @RequestParam(name = "pageSize", defaultValue = "7") int pageSize,
                                           @RequestParam(required = false) String search) {
        return this.categoryService.categories(pageNo, pageSize, search);
    }

    @PostMapping("/add")
    public void addCategory(@RequestBody CategoryDto categoryDto){
        categoryDto.setDateCreation(new Date());
        categoryDto.setDateModification(new Date());
        this.categoryService.addCategory(categoryDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable long id){
        this.categoryService.deleteCategory(id);
    }

    @PutMapping("/up-de/{id}")
    public void updateDeleteCategory(@PathVariable long id) throws Exception {
        this.categoryService.updateDelete(id);
    }

    @PutMapping("/update/{id}")
    public void updateCategory(@PathVariable long id, @RequestBody CategoryDto updatedCategory) throws Exception {
        this.categoryService.updateCategory(id, updatedCategory);
    }

    @GetMapping("/count")
    public int countCategories(){
        return this.categoryService.counting();
    }

    @GetMapping("/getCategory/{id}")
    public CategoryDto getCategory(@PathVariable Long id){
        return this.categoryService.getCategory(id);
    }
}
