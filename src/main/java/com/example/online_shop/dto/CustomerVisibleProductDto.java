package com.example.online_shop.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class CustomerVisibleProductDto {
    private UUID id;
    private String name;
    private String details;
    private String messageForQuantity;
    private BigDecimal price;


    public CustomerVisibleProductDto(){}

    public CustomerVisibleProductDto(UUID id, String name, String details, String messageForQuantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.messageForQuantity = messageForQuantity;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getMessageForQuantity() {
        return messageForQuantity;
    }

    public void setMessageForQuantity(String messageForQuantity) {
        this.messageForQuantity = messageForQuantity;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
