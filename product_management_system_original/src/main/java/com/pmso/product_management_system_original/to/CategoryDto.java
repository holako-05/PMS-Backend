package com.pmso.product_management_system_original.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    private String nom;

    private String description;

    private String slug;

    private Date dateCreation;

    private Date dateModification;

    private Boolean supprimer;

}
