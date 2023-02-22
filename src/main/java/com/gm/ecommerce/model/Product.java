package com.gm.ecommerce.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.HashMap;
import java.util.Objects;

@MappedSuperclass
public class Product {
   private @Id @GeneratedValue Long id;
   private String name;
   private Long stockCount;
   private Double price;

   public Product(String name, Long stockCount, Double price) {
      this.name = name;
      this.stockCount = stockCount;
      this.price = price;
   }

   public Product() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Long getStockCount() {
      return stockCount;
   }

   public void setStockCount(Long stockCount) {
      this.stockCount = stockCount;
   }

   public Double getPrice() {
      return price;
   }

   public void setPrice(Double price) {
      this.price = price;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Product product = (Product) o;
      return Objects.equals(id, product.id) && Objects.equals(name, product.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, name);
   }
}
