package com.pmsk.product_management_system_kafka.dataaccess.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String description;

    private String image;

    private String slug;

    @Column(name = "date_creation")
    private Date dateCreation;

    @Column(name = "date_modification")
    private Date dateModification;

    private Boolean supprimer;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
