package com.example.online_shop.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.math.BigDecimal;

public class ProductDto {
    private String name;

    private String details;

    private BigDecimal price;

    private Integer quantity;

    private Boolean active;

    private BigDecimal originalPrice;

    public ProductDto(){}

    public ProductDto(String name, String details, BigDecimal price, Integer quantity, Boolean active, BigDecimal originalPrice) {
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
        this.originalPrice = originalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
