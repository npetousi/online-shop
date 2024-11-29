package com.example.online_shop.dto;

import com.example.online_shop.model.AddressEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientDto {
    private String name;

    private String surname;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private AddressDto address;

    public ClientDto(){}

    public ClientDto(String name, String surname, String email, String phoneNumber, AddressDto address) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
