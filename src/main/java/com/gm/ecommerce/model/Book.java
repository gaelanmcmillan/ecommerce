package com.gm.ecommerce.model;


import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class Book extends Product {
    private String author;
    private String publisher;
    private Long pageCount;

    public Book() {
    }

    public Book(String name, Long stockCount, Double price, String author, String publisher, Long pageCount) {
        super(name, stockCount, price);
        this.author = author;
        this.publisher = publisher;
        this.pageCount = pageCount;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(author, book.author) && Objects.equals(publisher, book.publisher);
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", publisher='" + publisher + '\'' +
                ", pageCount=" + pageCount +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), author, publisher);
    }
}
