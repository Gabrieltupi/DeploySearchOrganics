package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;

import java.util.List;

public interface ICupomService {
    List<CupomDTO> findAll() throws Exception;

    CupomDTO findById(Integer idCupom) throws Exception;

    CupomDTO save(Integer idEmpresa, CreateCupomDTO cupomDto) throws Exception;

    CupomDTO update(Integer idCupom, UpdateCupomDTO cupomDto) throws Exception;

    List<CupomDTO> findAllByIdEmpresa(Integer idEmpresa) throws Exception;
}
