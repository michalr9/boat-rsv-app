package com.example.boatrsvapp.data.model;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Customer {
    private Long id;
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private Address address;
    private Collection<Licence> licences;
}
