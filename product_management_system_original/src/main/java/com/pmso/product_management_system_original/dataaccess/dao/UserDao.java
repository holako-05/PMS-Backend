package com.pmso.product_management_system_original.dataaccess.dao;

import com.pmso.product_management_system_original.dataaccess.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
