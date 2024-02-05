package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosMesDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRelatorioService {
    Page<RelatorioProdutoPrecoDTO> findAllProdutosByPreco(Pageable pageable) throws Exception;

    Page<RelatorioProdutoQuantidadeDTO> findAllProdutosByQuantidade(Pageable pageable) throws Exception;

    Page<RelatorioProdutoPedidosDTO> findAllProdutosByPedidos(Pageable pageable) throws Exception;

    Page<RelatorioProdutoPedidosMesDTO> findAllProdutosByPedidosByMes(Pageable pageable) throws Exception;
}
