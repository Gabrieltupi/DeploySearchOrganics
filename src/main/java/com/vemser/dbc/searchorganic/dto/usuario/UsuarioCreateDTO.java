package com.vemser.dbc.searchorganic.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class UsuarioCreateDTO {
    private Integer idUsuario;
    private String nome;
    private String sobrenome;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

        private Endereco endereco;
        private String cpf;
        private String email;
        private String login;
        private String senha;
        private TipoAtivo tipoAtivo = TipoAtivo.S;

}
