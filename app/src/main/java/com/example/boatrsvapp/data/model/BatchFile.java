package com.example.boatrsvapp.data.model;

import java.sql.Blob;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class BatchFile {
    private Long id;
    private Blob file;
}
