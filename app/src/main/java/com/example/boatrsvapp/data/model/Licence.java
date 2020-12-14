package com.example.boatrsvapp.data.model;

import java.time.LocalDate;

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
    private LocalDate cusBirthday;
}
