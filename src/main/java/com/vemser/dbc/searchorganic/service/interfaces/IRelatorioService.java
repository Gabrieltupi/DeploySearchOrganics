package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRelatorioService {
    Page<RelatorioProdutoDTO> findAllProdutosByPreco(Pageable pageable) throws Exception;
}
