package com.pmso.product_management_system_original.logic.impl;

import com.pmso.product_management_system_original.dataaccess.dao.ProductDao;
import com.pmso.product_management_system_original.dataaccess.entities.Category;
import com.pmso.product_management_system_original.dataaccess.entities.Product;
import com.pmso.product_management_system_original.exceptions.ResourceNotFoundException;
import com.pmso.product_management_system_original.logic.api.ProductService;
import com.pmso.product_management_system_original.producers.ProductProducer;
import com.pmso.product_management_system_original.to.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    //private final String IMAGES_PATH_ABSOLUTE = "C:/Users/Owner/Desktop/New/New PMS Docker/New_Dashboard/product_management_system_frontend/public/assets/images/";
    private final String IMAGES_PATH = "/app/images/";

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductProducer productProducer;

    @Override
    public void addProduct(ProductDto productDto) throws IOException {
        Product product = new Product();
        product.setNom(productDto.getNom());
        product.setDescription(productDto.getDescription());
        product.setDateCreation(new Date());

        String imagePath = productDto.getImage().getOriginalFilename();
        product.setImage(imagePath);

        File imageFile = new File(IMAGES_PATH + imagePath);
        productDto.getImage().transferTo(imageFile);
        //FileSystemUtils.copyRecursively(imageFile, new File(IMAGES_PATH_ABSOLUTE + imagePath));

        product.setSupprimer(false);
        product.setSlug(productDto.getNom().replace(" ", "-"));
        product.setDateModification(new Date());
        product.setCategory(new Category(productDto.getCategoryId()));
        this.productDao.save(product);
        this.productProducer.send(product);
    }

    @Override
    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setNom(product.getNom());
        productDto.setDescription(product.getDescription());
        productDto.setDateCreation(product.getDateCreation());
        productDto.setImagePath(product.getImage());
        productDto.setSupprimer(product.getSupprimer());
        productDto.setSlug(product.getSlug());
        productDto.setDateModification(product.getDateModification());
        if (product.getCategory() != null) {
            productDto.setCategoryId(product.getCategory().getId());
        }
        return productDto;
    }

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = this.productDao.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: products) {
            productDtos.add(this.getProductDto(product));
        }
        return productDtos;
    }

    @Override
    public ProductDto getProduct(Long id) {
        ProductDto productDto = new ProductDto();
        Product product = this.productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));
        productDto.setNom(product.getNom());
        productDto.setDescription(product.getDescription());
        productDto.setSlug(product.getSlug());
        productDto.setImagePath(product.getImage());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setDateCreation(product.getDateCreation());
        productDto.setDateModification(product.getDateModification());
        return productDto;
    }

    @Override
    public List<ProductDto> products() {
        List<Product> products = this.productDao.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: products) {
            if (!product.getSupprimer())
                productDtos.add(this.getProductDto(product));
        }
        return productDtos;
    }

    @Override
    public void updateProduct(long id, ProductDto productDto) throws Exception {
        Product product = this.productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));
        String slug;
        if (productDto.getNom() != null){
            product.setNom(productDto.getNom());
            slug = productDto.getNom().replace(" ", "-");
        } else {
            product.setNom(product.getNom());
            slug = product.getNom().replace(" ", "-");
        }
        if (productDto.getDescription() != null){
            product.setDescription(productDto.getDescription());
        } else {
            product.setDescription(product.getDescription());
        }
        String imagePath = productDto.getImage().getOriginalFilename();
        product.setImage(imagePath);

        File imageFile = new File(IMAGES_PATH + imagePath);
        productDto.getImage().transferTo(imageFile);

        product.setId(product.getId());
        product.setDateCreation(product.getDateCreation());
        product.setSupprimer(product.getSupprimer());
        product.setSlug(slug);
        product.setDateModification(new Date());
        product.setCategory(product.getCategory());
        this.productDao.save(product);
        this.productProducer.send(product);
    }

    @Override
    public void deleteProduct(long id) {
        this.productDao.deleteById(id);
    }

    @Override
    public int counting() {
        List<ProductDto> productDtos = this.products();
        return productDtos.size();
    }

    @Override
    public void deleteUpdateProduct(long id) {
        Product product = this.productDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product " + id + " not found"));
        product.setSupprimer(true);
        product.setDateModification(new Date());
        this.productDao.save(product);
        this.productProducer.send(product);
    }

}
