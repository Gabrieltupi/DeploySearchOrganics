package com.vemser.dbc.searchorganic.dto.log;

import com.vemser.dbc.searchorganic.utils.TipoLog;
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
    private TipoLog tipoLog;
    private String idUsuario;
    private String acao;
    private String payload;
    private LocalDateTime data;
}
