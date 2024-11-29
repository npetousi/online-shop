package com.example.online_shop.service;

import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.exception.OriginalPriceNotFoundException;
import com.example.online_shop.exception.ProductNotFoundException;
import com.example.online_shop.mapper.ProductMapper;
import com.example.online_shop.model.ProductEntity;
import com.example.online_shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@Primary
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final ProductRepository productRepository;

    public AdminServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(ProductDto productDto) {
        productRepository.save(ProductMapper.toEntity(productDto));
    }

    @Override
    public void restockProduct(UUID productId, Integer quantity) {
        if (quantity == null || quantity < 0){
            throw new IllegalArgumentException("Quantity must be a positive integer.");
        }
        ProductEntity product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product with id " + productId +" not found.")
        );
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
    }

    @Override
    public void deactivateProduct(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product with id " + productId +" not found.")
        );
        product.setActive(false);
        productRepository.save(product);
    }

    @Override
    public void reactivateProduct(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product with id " + productId +" not found.")
        );
        product.setActive(true);
        productRepository.save(product);
    }

    @Override
    public void calculateAndSetSalesPrice(UUID productId, Integer salesPercentage) {
       ProductEntity product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product with id " + productId +" not found.")
        );
        product.setOriginalPrice(product.getPrice());
        BigDecimal newPrice = calculatingPrice(product.getPrice(), salesPercentage);
        product.setPrice(newPrice);
        productRepository.save(product);

    }
    private BigDecimal calculatingPrice(BigDecimal price, Integer salesPercentage){
        BigDecimal percentage = BigDecimal.valueOf(salesPercentage).divide(BigDecimal.valueOf(100));
        BigDecimal subtractedPrice = price.multiply(percentage);
        return price.subtract(subtractedPrice);
    }

    @Override
    public void restorePrice(UUID productId) {
        ProductEntity product = productRepository.findById(productId).orElseThrow(
                ()-> new ProductNotFoundException("Product with id " + productId +" not found.")
        );
        if (product.getOriginalPrice() == null) {
            throw new OriginalPriceNotFoundException("Original price for product with id " + product + " is not set.");
        }
        product.setPrice(product.getOriginalPrice());
        product.setOriginalPrice(null);
        productRepository.save(product);
    }
}
