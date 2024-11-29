package com.example.online_shop.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "product", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "details"}) })
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private  String details;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "original_price")
    private BigDecimal originalPrice;

    public ProductEntity(){}

    public ProductEntity(UUID id, String name, String details, BigDecimal price,
                         Integer quantity, Boolean active, BigDecimal originalPrice) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.quantity = quantity;
        this.active = active;
        this.originalPrice = originalPrice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details=" + details +
                ", price=" + price +
                ", quantity=" + quantity +
                ", active=" + active +
                ", originalPrice=" + originalPrice +
                '}';
    }

    public String showMessageForQuantity(){
        if (this.quantity == 0){
            return "Out of stock.";
        } else if (quantity < 5){
            return "Latest items.";
        } else {
            return null;
        }
    }
}
