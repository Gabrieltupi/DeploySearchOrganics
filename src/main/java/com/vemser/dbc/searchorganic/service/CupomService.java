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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CupomService implements ICupomService {
    private final CupomRepository cupomRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public Page<CupomDTO> findAll(Pageable pageable) throws Exception {
        Page<Cupom> cupons = cupomRepository.findAll(pageable);
        return cupons.map(this::retornarDto);
    }

    public CupomDTO findById(Integer idCupom) throws Exception {
        Cupom cupom = getById(idCupom);
        return retornarDto(cupom);
    }

    public Cupom getById(Integer idCupom) throws Exception {
        return cupomRepository.findById(idCupom).orElseThrow(() -> new RegraDeNegocioException("Não encontrado"));
    }

    public CupomDTO save(Integer idEmpresa, CreateCupomDTO cupomDto) {
        Cupom cupom = objectMapper.convertValue(cupomDto, Cupom.class);
        cupom.setIdEmpresa(idEmpresa);

        return retornarDto(cupomRepository.save(cupom));
    }

    public CupomDTO update(Integer idCupom, UpdateCupomDTO cupomDto) throws Exception {
        Cupom cupomTransicao = this.getById(idCupom);
        Integer idC=cupomDto.getIdEmpresa();

        if (cupomTransicao.getIdEmpresa().equals(idC)) {
            Integer cupomId =idC;

            if (idCupom.equals(cupomId) || usuarioService.isAdmin()) {
                Cupom cupom = objectMapper.convertValue(cupomDto, Cupom.class);
                cupom.setIdCupom(idCupom);

                return retornarDto(cupomRepository.save(cupom));
            } else {
                throw new RegraDeNegocioException("O cupom informado não está associado ao usuário logado.");
            }
        } else {
            throw new RegraDeNegocioException("Não foi encontrado um cupom válido associado na empresa informada.");
        }
    }

    public Page<CupomDTO> findAllByIdEmpresa(Integer idEmpresa, Pageable pageable) throws Exception {
        Page<Cupom> cupons = cupomRepository.findAllByIdEmpresa(idEmpresa, pageable);
        return cupons.map(this::retornarDto);
    }

    private CupomDTO retornarDto(Cupom entity) {
        return objectMapper.convertValue(entity, CupomDTO.class);
    }
}







