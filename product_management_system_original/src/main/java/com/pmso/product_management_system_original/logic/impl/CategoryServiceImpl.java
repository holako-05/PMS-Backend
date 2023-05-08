package com.pmso.product_management_system_original.logic.impl;

import com.pmso.product_management_system_original.dataaccess.dao.CategoryDao;
import com.pmso.product_management_system_original.dataaccess.dao.ProductDao;
import com.pmso.product_management_system_original.dataaccess.entities.Category;
import com.pmso.product_management_system_original.dataaccess.entities.Product;
import com.pmso.product_management_system_original.exceptions.DeletingException;
import com.pmso.product_management_system_original.exceptions.ResourceNotFoundException;
import com.pmso.product_management_system_original.logic.api.CategoryService;
import com.pmso.product_management_system_original.producers.CategoryProducer;
import com.pmso.product_management_system_original.to.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryProducer categoryProducer;

    String slug;

    @Override
    public void addCategory(CategoryDto categoryDto) {
        Category category = new Category();
        categoryDto.setSupprimer(false);
        category.setNom(categoryDto.getNom());
        category.setDescription(categoryDto.getDescription());
        category.setSlug(categoryDto.getNom().replace(" ", "-"));
        category.setSupprimer(categoryDto.getSupprimer());
        category.setDateModification(new Date());
        category.setDateCreation(new Date());
        this.categoryDao.save(category);
        this.categoryProducer.send(category);
    }

    @Override
    public CategoryDto getCategoryDto(Category category){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setNom(category.getNom());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setDateCreation(category.getDateCreation());
        categoryDto.setSlug(category.getSlug());
        categoryDto.setSupprimer(category.getSupprimer());
        categoryDto.setDateModification(category.getDateModification());
        return categoryDto;
    }

    @Override
    public CategoryDto getCategory(Long id) {
        CategoryDto categoryDto = new CategoryDto();
        Category category = this.categoryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category " + id + " not found"));
        categoryDto.setNom(category.getNom());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setDateCreation(category.getDateCreation());
        categoryDto.setSlug(category.getSlug());
        categoryDto.setDateModification(category.getDateModification());
        return categoryDto;
    }

    @Override
    public List<CategoryDto> allCategories() {
        List<Category> categories = this.categoryDao.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category: categories) {
            if (!category.getSupprimer())
                categoryDtos.add(this.getCategoryDto(category));
        }
        return categoryDtos;
    }

    @Override
    public List<CategoryDto> categories(int pageNo, int pageSize, String search) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        if (search != null && !search.isBlank()) {
            List<Category> categories;
            categories = this.categoryDao.findByNomContainingIgnoreCase(search);
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for (Category category: categories) {
                if (!category.getSupprimer())
                    categoryDtos.add(this.getCategoryDto(category));
            }
            return categoryDtos;
        } else {
            Page<Category> categories;
            categories = this.categoryDao.findAll(pageable);
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for (Category category: categories) {
                if (!category.getSupprimer())
                    categoryDtos.add(this.getCategoryDto(category));
            }
            return categoryDtos;
        }

    }

    @Override
    public void updateCategory(long id, CategoryDto updatedCategory){
        Category category = this.categoryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category " + id + " not found"));
        if (updatedCategory.getNom() != null) {
            category.setNom(updatedCategory.getNom());
            slug = updatedCategory.getNom().replace(" ", "-");
        } else {
            category.setNom(category.getNom());
            slug = category.getNom().replace(" ", "-");
        }
        if (updatedCategory.getDescription() != null) {
            category.setDescription(updatedCategory.getDescription());
        } else {
            category.setDescription(category.getDescription());
        }
        category.setId(category.getId());
        category.setSlug(slug);
        category.setDateModification(new Date());
        category.setDateCreation(category.getDateCreation());
        this.categoryDao.save(category);
        this.categoryProducer.send(category);
    }

    @Override
    public void deleteCategory(long id) {
        this.categoryDao.deleteById(id);
    }

    @Override
    public void updateDelete(long id) {
        List<Product> products = this.productDao.findByCategory_Id(id);
        if (!products.isEmpty()) {
            throw new DeletingException("Cannot delete category " + id + " because it is referenced in the product table");
        } else {
            Category category = this.categoryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category " + id + " not found"));
            category.setSupprimer(true);
            category.setDateModification(new Date());
            this.categoryDao.save(category);
            this.categoryProducer.send(category);
        }

    }

    @Override
    public int counting() {
        List<CategoryDto> categoryDtos = this.allCategories();
        return categoryDtos.size();
    }
}
