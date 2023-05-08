package com.pmso.product_management_system_original.logic.api;

import com.pmso.product_management_system_original.dataaccess.entities.Product;
import com.pmso.product_management_system_original.to.ProductDto;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    void addProduct(ProductDto productDto) throws IOException;
    ProductDto getProductDto(Product product);
    List<ProductDto> getProducts();
    void updateProduct(long id, ProductDto productDto) throws Exception;
    void deleteProduct(long id);
    int counting();
    List<ProductDto> products();
    ProductDto getProduct(Long id);
    void deleteUpdateProduct(long id) throws Exception;
}
