package com.vemser.dbc.searchorganic.dto.empresa;

import com.vemser.dbc.searchorganic.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmpresaDTO {

    private Integer idEmpresa;
    private Integer idUsuario;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;
    private ArrayList<Produto> produtos = new ArrayList<>();


}
