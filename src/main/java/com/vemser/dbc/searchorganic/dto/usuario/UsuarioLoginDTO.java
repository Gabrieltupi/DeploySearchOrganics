package com.vemser.dbc.searchorganic.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO {
    @NotNull
    @NotBlank
    private String login;
    @NotNull
    @NotBlank
    private String senha;
}
