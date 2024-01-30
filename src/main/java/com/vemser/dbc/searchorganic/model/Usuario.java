package com.vemser.dbc.searchorganic.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(hidden = true)
@Entity(name = "USUARIO")
public class Usuario {
    @Id
    private Integer idUsuario;
    @Schema(description = "Nome do usuario", required = true, example = "Gabriel Antonio")
    private String nome;

    @NotBlank
    @Schema(description = "Sobrenome do usuario", required = true, example = "Nunes de Souza")
    private String sobrenome;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data de nascimento do usuario", required = true, example = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @OneToMany(mappedBy = "idUsuario")
    private List<Endereco> enderecos;

    @CPF
    @Schema(description = "CPF", required = true, example = "464.732.190-80")
    private String cpf;

    @NotNull
    @Schema(description = "Email", required = true, example = "Gabriel.nunes@dbccompany.com.br")
    private String email;

    @NotNull
    @NotBlank
    @Schema(description = "Login", required = true, example = "Deyvid_Uzumaki321")
    private String login;

    @NotNull
    @NotBlank
    @Schema(description = "Senha", required = true, example = "*********")
    private String senha;

    @Schema(description = "Atividade do usuario", required = true, example = "S")
    private TipoAtivo tipoAtivo = TipoAtivo.S;
}
