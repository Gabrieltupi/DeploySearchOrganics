package com.vemser.dbc.searchorganic.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UsuarioUpdateDTO {

    private Integer idUsuario;
    @NotBlank
    @Schema(description = "Nome do usuario", required = true, example = "Gabriel Antonio")
    private String nome;


    @NotBlank
    @Schema(description = "Sobrenome do usuario", required = true, example = "Nunes de Souza")
    private String sobrenome;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data de nascimento do usuario", required = true, example = "yyyy-MM-dd")
    private LocalDate dataNascimento;


//    private Endereco endereco;

    @CPF
    @Schema(description = "CPF", required = true, example = "464.732.190-80")
    private String cpf;

    @NotNull
    @Schema(description = "Email", required = true, example = "Gabriel.nunes@dbccompany.com.br")
    private String email;

    @NotNull
    @NotBlank
    @Schema(description = "Login",required = true,example = "Deyvid_Uzumaki321")
    private String login;

    @NotNull
    @NotBlank
    @Schema(description = "Senha", required = true, example = "*********")
    private String senha;

    @Schema(description = "Atividade do usuario", required = true, example = "S")
    private TipoAtivo tipoAtivo = TipoAtivo.S;
}
