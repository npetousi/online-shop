package com.example.online_shop.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "purchase_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientEntity client;

    @ElementCollection
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<ProductEntity, Integer> productsWithQuantity = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "payment")
    private BigDecimal payment;

    @Column(name = "change")
    private BigDecimal change;

    public OrderEntity(){}

    public OrderEntity(UUID id, ClientEntity client, Map<ProductEntity, Integer> productsWithQuantity,
                       AddressEntity address, BigDecimal totalCost, BigDecimal payment, BigDecimal change) {
        this.id = id;
        this.client = client;
        this.productsWithQuantity = productsWithQuantity;
        this.address = address;
        this.totalCost = totalCost;
        this.payment = payment;
        this.change = change;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public Map<ProductEntity, Integer> getProductsWithQuantity() {
        return productsWithQuantity;
    }

    public void setProductsWithQuantity(Map<ProductEntity, Integer> productsWithQuantity) {
        this.productsWithQuantity = productsWithQuantity;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
