package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.model.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEmpresaService {
    Page<EmpresaDTO> findAll(Pageable pageable) throws Exception;

    EmpresaDTO findById(Integer idEmpresa) throws Exception;

    EmpresaDTO save(Integer idUsuario, CreateEmpresaDTO empresaDto) throws Exception;

    EmpresaDTO update(Integer idEmpresa, UpdateEmpresaDTO empresaDto) throws Exception;

    void delete(Integer idEmpresa) throws Exception;

    Page<EmpresaProdutosDTO> findAllWithProdutos(Pageable pageable) throws Exception;

    EmpresaProdutosDTO findByIdWithProdutos(Integer idEmpresa) throws Exception;
}
