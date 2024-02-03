package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.repository.CupomRepository;
import com.vemser.dbc.searchorganic.service.interfaces.ICupomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CupomService implements ICupomService {
    private final CupomRepository cupomRepository;
    private final ObjectMapper objectMapper;

    public List<CupomDTO> findAll() throws Exception {
        List<Cupom> cupons = cupomRepository.findAll();
        return objectMapper.convertValue(cupons, objectMapper.getTypeFactory().constructCollectionType(List.class, CupomDTO.class));
    }

    public CupomDTO findById(Integer idCupom) throws Exception {
        Cupom cupom = getById(idCupom);
        return retornarDTO(cupom);
    }

    public CupomDTO save(Integer idEmpresa, CreateCupomDTO cupomDto) {
        Cupom cupom = objectMapper.convertValue(cupomDto, Cupom.class);
        cupom.setIdEmpresa(idEmpresa);

        return retornarDTO(cupomRepository.save(cupom));
    }

    public CupomDTO update(Integer idCupom, UpdateCupomDTO cupomDto) throws Exception {
        Cupom cupom = objectMapper.convertValue(cupomDto, Cupom.class);
        cupom.setIdCupom(idCupom);

        return retornarDTO(cupomRepository.save(cupom));
    }

    public List<CupomDTO> findAllByIdEmpresa(Integer idEmpresa) throws Exception {
        List<Cupom> cupons = cupomRepository.findAllByIdEmpresa(idEmpresa);
        return objectMapper.convertValue(cupons, objectMapper.getTypeFactory().constructCollectionType(List.class, CupomDTO.class));
    }

    private CupomDTO retornarDTO(Cupom entity) {
        return objectMapper.convertValue(entity, CupomDTO.class);
    }

    public Cupom getById(Integer idCupom) throws Exception {
        return cupomRepository.findById(idCupom).orElseThrow(() -> new RegraDeNegocioException("Não encontrado"));
    }
}







