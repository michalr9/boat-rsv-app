package com.example.boatrsvapp.data.model;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Product {
    private Long id;
    private String name;
    private Integer price;
    private String imgUrl;
    private String description;
    private String technicalData;
    private String locationName;
    private Collection<BatchFile> files;
}
