package com.example.online_shop.service;

import com.example.online_shop.dto.ClientDto;
import com.example.online_shop.dto.CustomerVisibleProductDto;
import com.example.online_shop.dto.OrderRequestDto;
import com.example.online_shop.dto.PurchaseRequestDto;

import java.util.List;

public interface ClientService {

    public List<CustomerVisibleProductDto> getAllProducts();
    public String purchase(PurchaseRequestDto purchaseRequestDto);


    public void addClient(ClientDto clientDto);


}
