package com.pmso.product_management_system_original;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Product Management System", version = "2.0", description = "PMS Information", contact = @Contact(name = "Youssef ELHILALI", email = "y.elhilali24@gmail.com") ))
public class ProductManagementSystemOriginalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementSystemOriginalApplication.class, args);
    }

}
