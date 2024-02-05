package com.vemser.dbc.searchorganic.dto.empresa;

import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaProdutosDTO extends EmpresaDTO {
    @Schema(description = "Produtos", required = true, example = "Produtos")
    private List<ProdutoDTO> produtos;
}
