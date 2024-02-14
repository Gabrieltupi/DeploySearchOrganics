package com.vemser.dbc.searchorganic.dto.usuario;

import com.vemser.dbc.searchorganic.model.Cargo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {
    @NotBlank
    @NotNull
    private String nome;

    public CargoDTO(Cargo cargo) {
        this.nome = cargo.getNome();
    }
}
