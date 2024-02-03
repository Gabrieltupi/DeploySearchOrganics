package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;

import java.util.List;

public interface IProdutoService {
    List<ProdutoDTO> findAll() throws Exception;

    ProdutoDTO findById(Integer id) throws Exception;

    ProdutoDTO save(Integer idEmpresa, ProdutoCreateDTO produtoDto) throws Exception;

    ProdutoDTO update(Integer idProduto, ProdutoUpdateDTO produtoDto) throws Exception;

    void delete(Integer idProduto) throws Exception;

    List<ProdutoDTO> findAllByIdEmpresa(Integer idEmpresa) throws Exception;

    List<ProdutoDTO> findAllByIdCategoria(Integer idCategoria) throws Exception;
}
