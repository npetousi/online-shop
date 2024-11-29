package com.example.online_shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressDto {

    private String country;

    private String state;

    private String city;

    @JsonProperty("postal_code")
    private String postalCode;

    @JsonProperty("street_address")
    private String StreetAddress;

    public AddressDto(String country, String state, String city, String postalCode, String streetAddress) {
        this.country = country;
        this.state = state;
        this.city = city;
        this.postalCode = postalCode;
        StreetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }
}
