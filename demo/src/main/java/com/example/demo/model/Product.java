package com.example.demo.model;

import jakarta.persistence.*;
import java.util.*;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String link;
    private Double rating;
    private Double price;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "website_id")
    private Website website;

    // Getters and Setters

}
