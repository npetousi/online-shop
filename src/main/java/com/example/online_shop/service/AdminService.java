package com.example.online_shop.service;

import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.model.ProductEntity;

import java.util.UUID;

public interface AdminService {
    public void addProduct(ProductDto productDto);
    public void restockProduct(UUID productId, Integer quantity);
    public void reactivateProduct(UUID productId);
    public void deactivateProduct(UUID productId);
    public void calculateAndSetSalesPrice(UUID productId, Integer pricePercentage);
    public void restorePrice(UUID productId);

}
