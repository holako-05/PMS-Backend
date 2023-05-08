package com.pmso.product_management_system_original.dataaccess.dao;

import com.pmso.product_management_system_original.dataaccess.entities.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {
    Page<Category> findAll(Pageable pageable);
    List<Category> findByNomContainingIgnoreCase(String search);
}
