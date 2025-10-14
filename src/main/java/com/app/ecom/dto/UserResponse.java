package com.app.ecom.dto;

import com.app.ecom.model.UserRole;

public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDto addressDto;

}
