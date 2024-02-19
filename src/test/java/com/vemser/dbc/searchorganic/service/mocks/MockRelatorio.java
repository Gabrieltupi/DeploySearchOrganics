package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPedidosMesDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoPrecoDTO;
import com.vemser.dbc.searchorganic.dto.relatorio.RelatorioProdutoQuantidadeDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MockRelatorio {
    public static List<RelatorioProdutoPrecoDTO>  retornarListaRelatorioPreco(){
        List<RelatorioProdutoPrecoDTO> relatorioDTOs = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            RelatorioProdutoPrecoDTO relatorioDTO = new RelatorioProdutoPrecoDTO("produto" + i, BigDecimal.valueOf(i  * 2));
            relatorioDTOs.add(relatorioDTO);
        }
      return relatorioDTOs;
    }
    public static List<RelatorioProdutoQuantidadeDTO>  retornarListaRelatorioQuantidade(){
        List<RelatorioProdutoQuantidadeDTO> relatorioDTOs = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            RelatorioProdutoQuantidadeDTO relatorioDTO = new RelatorioProdutoQuantidadeDTO("produto" + i, BigDecimal.valueOf(i  * 2));
            relatorioDTOs.add(relatorioDTO);
        }
        return relatorioDTOs;
    }
    public static List<RelatorioProdutoPedidosDTO>  retornarListaRelatorioPedido(){
        List<RelatorioProdutoPedidosDTO> relatorioDTOs = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            RelatorioProdutoPedidosDTO relatorioDTO = new RelatorioProdutoPedidosDTO("produto" + i, (long) (i  * 2));
            relatorioDTOs.add(relatorioDTO);
        }
        return relatorioDTOs;
    }
    public static List<RelatorioProdutoPedidosMesDTO>  retornarListaRelatorioMes(){
        List<RelatorioProdutoPedidosMesDTO> relatorioDTOs = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            RelatorioProdutoPedidosMesDTO relatorioDTO = new RelatorioProdutoPedidosMesDTO("produto" + i, "janeiro" , (long) (i  * 2));
            relatorioDTOs.add(relatorioDTO);
        }
        return relatorioDTOs;
    }
    public static List<Object[]> retornarResultadoObjectRepository(){
        List<Object[]> listaProdutos = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            Object[] produto = {"produto" + i, BigDecimal.valueOf(i  * 2)};
            listaProdutos.add(produto);
        }
        return listaProdutos;
    }
    public static List<Object[]> retornarResultadoObjectRepositoryInt(){
        List<Object[]> listaProdutos = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            Object[] produto = {"produto" + i, (long) i  * 2};
            listaProdutos.add(produto);
        }
        return listaProdutos;
    }
    public static List<Object[]> retornarResultadoObjectRepositoryMes(){
        List<Object[]> listaProdutos = new ArrayList<>();
        for(int i = 1; i < 4; i++){
            Object[] produto = {"produto" + i, "janeiro" , (long) i  * 2};
            listaProdutos.add(produto);
        }
        return listaProdutos;
    }
}
