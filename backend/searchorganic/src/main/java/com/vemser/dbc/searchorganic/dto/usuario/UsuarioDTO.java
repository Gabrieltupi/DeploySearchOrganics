package com.vemser.dbc.searchorganic.dto.usuario;

import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private  Integer idUsuario;
    private String nome;
    private String sobrenome ;
    private LocalDate dataNascimento;
    private String login;
    private TipoAtivo tipoAtivo = TipoAtivo.S;
}
