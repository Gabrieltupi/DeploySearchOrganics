package com.vemser.dbc.searchorganic.model;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario{
    private  Integer idUsuario;
    private String nome;
    private String sobrenome ;
    private Endereco endereco;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String login;
    private String senha;
    private TipoAtivo tipoAtivo = TipoAtivo.S;
}
