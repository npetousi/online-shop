package com.example.online_shop.mapper;

import com.example.online_shop.dto.ClientDto;
import com.example.online_shop.model.ClientEntity;

public class ClientMapper {
    public static ClientEntity toEntity(ClientDto clientDto){
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setName(clientDto.getName());
        clientEntity.setSurname(clientDto.getSurname());
        clientEntity.setEmail(clientDto.getEmail());
        clientEntity.setPhoneNumber(clientDto.getPhoneNumber());
        return clientEntity;
    }
}
