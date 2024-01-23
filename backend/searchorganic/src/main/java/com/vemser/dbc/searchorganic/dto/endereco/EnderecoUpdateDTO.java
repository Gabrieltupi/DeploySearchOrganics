package com.vemser.dbc.searchorganic.dto.endereco;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
public class UpdateCreateDTO {
    @NotNull
    private String logradouro;
    @NotNull
    private String numero;
    @NotNull
    private String complemento;
    @NotNull
    private String cidade;
    @NotNull
    private String estado;
    @NotNull
    private String cep;
    @NotNull
    private String bairros;
}
