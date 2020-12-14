package com.example.boatrsvapp.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Address {
    private Long id;
    private String street;
    private String num;
    private String city;
    private String zipCode;
}
