package com.vemser.dbc.searchorganic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Relatorio<T> {
    @Id
    private String id;
    private String nome;
    private T dados;
    private LocalDateTime data = LocalDateTime.now();
}
