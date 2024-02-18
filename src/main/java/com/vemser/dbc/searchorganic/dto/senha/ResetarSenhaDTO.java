package com.vemser.dbc.searchorganic.dto.senha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetarSenhaDTO {
    String senhaAtual;
    String novaSenha;
}
