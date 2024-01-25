package com.vemser.dbc.searchorganic.dto.empresa;

import com.vemser.dbc.searchorganic.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {
    private Integer idEmpresa;
    private Integer idUsuario;
    private String nomeFantasia;
    private String cnpj;
    private String razaoSocial;
    private String inscricaoEstadual;
    private String setor;
    private ArrayList<Produto> produtos = new ArrayList<>();
}
