package com.ordermanagementsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {
    private String houseNo;
    private String area;
    private String pincode;
    private String city;
    private String state;
    private String country;
}
