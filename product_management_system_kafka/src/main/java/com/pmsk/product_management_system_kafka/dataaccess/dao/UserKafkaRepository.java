package com.pmsk.product_management_system_kafka.dataaccess.dao;

import com.pmsk.product_management_system_kafka.dataaccess.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKafkaRepository extends JpaRepository<User, Long> {
}
