package com.vemser.dbc.searchorganic.dto.log;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
    private String id;
    private String nome;
    private LocalDateTime data;
}
