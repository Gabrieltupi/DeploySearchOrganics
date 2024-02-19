package com.vemser.dbc.searchorganic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class Log {
    @Id
    private String id;
    private String nome;
    private LocalDate data;
}
