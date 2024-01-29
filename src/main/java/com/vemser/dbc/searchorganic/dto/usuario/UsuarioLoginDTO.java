package com.vemser.dbc.searchorganic.dto.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO {
    @NotNull
    @NotBlank
    @Schema(description = "Login", required = true, example = "Tupi_Uzumaki321")
    private String login;

    @NotNull
    @NotBlank
    @Schema(description = "Senha", required = true, example = "*********")
    private String senha;
}
