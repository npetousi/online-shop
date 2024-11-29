package com.example.online_shop.mapper;

import com.example.online_shop.dto.CustomerVisibleProductDto;
import com.example.online_shop.dto.ProductDto;
import com.example.online_shop.model.ProductEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static CustomerVisibleProductDto toDto(ProductEntity product) {
        CustomerVisibleProductDto productDto = new CustomerVisibleProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDetails((product.getDetails()));
        productDto.setPrice(product.getPrice());
        productDto.setMessageForQuantity(product.showMessageForQuantity());
        return productDto;
    }
    public static ProductEntity toEntity(ProductDto productDto) {
        ProductEntity product = new ProductEntity();
        product.setName(productDto.getName());
        product.setDetails(productDto.getDetails());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setActive(productDto.getActive());
        product.setOriginalPrice(productDto.getOriginalPrice());
        return product;
    }

    public static List<CustomerVisibleProductDto> toDoToList(List<ProductEntity> products){
        return products.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

}
