package com.example.online_shop.mapper;


import com.example.online_shop.dto.AddressDto;
import com.example.online_shop.model.AddressEntity;

public class AddressMapper {

    public static AddressEntity toEntity(AddressDto addressDto) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCountry(addressDto.getCountry());
        addressEntity.setState(addressDto.getState());
        addressEntity.setCity(addressDto.getCity());
        addressEntity.setPostalCode(addressDto.getPostalCode());
        addressEntity.setStreetAddress(addressDto.getStreetAddress());
        return addressEntity;
    }
}
