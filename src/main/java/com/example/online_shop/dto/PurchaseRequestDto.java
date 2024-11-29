package com.example.online_shop.dto;

public class PurchaseRequestDto {

    private ClientDto clientDto;

    private OrderRequestDto orderRequestDto;

    public PurchaseRequestDto(){}

    public PurchaseRequestDto(ClientDto clientDto, OrderRequestDto orderRequestDto) {
        this.clientDto = clientDto;
        this.orderRequestDto = orderRequestDto;
    }

    public ClientDto getClientDto() {
        return clientDto;
    }

    public void setClientDto(ClientDto clientDto) {
        this.clientDto = clientDto;
    }

    public OrderRequestDto getOrderRequestDto() {
        return orderRequestDto;
    }

    public void setOrderRequestDto(OrderRequestDto orderRequestDto) {
        this.orderRequestDto = orderRequestDto;
    }
}
