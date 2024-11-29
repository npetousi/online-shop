package com.example.online_shop.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class OrderRequestDto {

    private Map<UUID, Integer> productsWithQuantity;

    private BigDecimal payment;

    public OrderRequestDto() {}


    public OrderRequestDto(Map<UUID, Integer> productsWithQuantity, BigDecimal payment) {
        this.productsWithQuantity = productsWithQuantity;
        this.payment = payment;
    }

    public Map<UUID, Integer> getProductsWithQuantity() {
        return productsWithQuantity;
    }

    public void setProductsWithQuantity(Map<UUID, Integer> productsWithQuantity) {
        this.productsWithQuantity = productsWithQuantity;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}

