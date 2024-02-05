package com.vemser.dbc.searchorganic.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class UsuarioCreateDTO {
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

    @Schema(description = "CPF", required = true, example = "84033577025")
    private String cpf;

    @NotNull
    @Schema(description = "Email", required = true, example = "umdiferente@dbccompany.com.br")
    private String email;

    @NotNull
    @NotBlank
    @Schema(description = "Login", required = true, example = "um.diferen.te")
    private String login;

    @NotNull
    @NotBlank
    @Schema(description = "Senha", required = true, example = "*********")
    private String senha;

    @Schema(description = "Atividade do usuario", required = true, example = "S")
    private TipoAtivo tipoAtivo = TipoAtivo.S;
}
