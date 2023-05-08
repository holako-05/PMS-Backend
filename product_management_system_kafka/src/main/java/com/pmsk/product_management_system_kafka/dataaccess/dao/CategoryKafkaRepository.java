package com.pmsk.product_management_system_kafka.dataaccess.dao;

import com.pmsk.product_management_system_kafka.dataaccess.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryKafkaRepository extends JpaRepository<Category, Long> {
}
