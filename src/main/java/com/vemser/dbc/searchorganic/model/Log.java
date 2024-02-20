package com.vemser.dbc.searchorganic.model;

import com.vemser.dbc.searchorganic.utils.TipoLog;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@Document
public class Log {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private TipoLog tipoLog;

    private String idUsuario;
    private String acao;
    private String payload;
    private LocalDateTime data;

    public Log(String idUsuario, String acao, TipoLog tipoLog, String payload) {
        this.idUsuario = idUsuario;
        this.acao = acao;
        this.tipoLog = tipoLog;
        this.payload = payload;
        this.data = LocalDateTime.now();
    }
}
