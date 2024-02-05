package com.vemser.dbc.searchorganic.service.interfaces;

import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICupomService {
    Page<CupomDTO> findAll(Pageable pageable) throws Exception;

    CupomDTO findById(Integer idCupom) throws Exception;

    CupomDTO save(Integer idEmpresa, CreateCupomDTO cupomDto) throws Exception;

    CupomDTO update(Integer idCupom, UpdateCupomDTO cupomDto) throws Exception;

    Page<CupomDTO> findAllByIdEmpresa(Integer idEmpresa, Pageable pageable) throws Exception;
}
