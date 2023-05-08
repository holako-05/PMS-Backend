package com.pmso.product_management_system_original.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String nom;

    private String description;

    private String imagePath;

    private MultipartFile image;

    private String slug;

    private Date dateCreation;

    private Date dateModification;

    private Boolean supprimer;

    private Long categoryId;

}
