package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProdutoService {
    Page<ProdutoDTO> findAll(Pageable pageable) throws Exception;

    ProdutoDTO findById(Integer id) throws Exception;

    ProdutoDTO save(Integer idEmpresa, ProdutoCreateDTO produtoDto) throws Exception;

    ProdutoDTO update(Integer idProduto, ProdutoUpdateDTO produtoDto) throws Exception;

    void delete(Integer idProduto) throws Exception;

    Page<ProdutoDTO> findAllByIdEmpresa(Integer idEmpresa, Pageable pageable) throws Exception;

    Page<ProdutoDTO> findAllByIdCategoria(Integer idCategoria, Pageable pageable) throws Exception;
}
