package com.vemser.dbc.searchorganic.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Log {
    @Id
    private String id;
    private String nome;
    private LocalDateTime data;

    public Log(String nome) {
        this.nome = nome;
        this.data = LocalDateTime.now();
    }
}
