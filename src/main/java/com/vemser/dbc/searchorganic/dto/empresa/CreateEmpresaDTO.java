package com.vemser.dbc.searchorganic.dto.empresa;


import com.vemser.dbc.searchorganic.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmpresaDTO {

    private Integer idEmpresa;
    private Integer idUsuario;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;
}


