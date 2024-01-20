package com.vemser.dbc.searchorganic.dto.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO {
    @NotNull
    @NotEmpty
    private String login;
    @NotNull
    @NotEmpty
    private String senha;
}
