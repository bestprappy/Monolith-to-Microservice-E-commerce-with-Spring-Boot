package com.app.ecom.dto.user;

import lombok.Data;

@Data
public class AddressDto {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
