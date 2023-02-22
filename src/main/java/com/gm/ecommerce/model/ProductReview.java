package com.gm.ecommerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ProductReview {
    private @Id @GeneratedValue Long id;
    private String review;
    private Long authorId;
    private Long productId;
    private Long rating;
}
