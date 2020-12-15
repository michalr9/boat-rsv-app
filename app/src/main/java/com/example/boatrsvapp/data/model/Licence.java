package com.example.boatrsvapp.data.model;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Licence {
    private Long id;
    private Long cusId;
    private String number;
    private String kind;
    private String[] cusBirthday;
}
