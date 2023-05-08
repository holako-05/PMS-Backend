package com.pmsk.product_management_system_kafka.dataaccess.dao;

import com.pmsk.product_management_system_kafka.dataaccess.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductKafkaRepository extends JpaRepository<Product, Long> {
}
