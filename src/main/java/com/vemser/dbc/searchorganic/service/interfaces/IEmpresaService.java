package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;

import java.util.List;

public interface IEmpresaService {
    List<EmpresaDTO> findAll() throws Exception;

    EmpresaDTO findById(Integer idEmpresa) throws Exception;

    EmpresaDTO save(Integer idUsuario, CreateEmpresaDTO empresaDto) throws Exception;

    EmpresaDTO update(Integer idEmpresa, UpdateEmpresaDTO empresaDto) throws Exception;

    void delete(Integer idEmpresa) throws Exception;
}
