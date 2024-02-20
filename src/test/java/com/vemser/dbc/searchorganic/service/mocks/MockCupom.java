package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class MockCupom {
    public static Cupom retornarCupom(){
        Cupom cupom = new Cupom();
        cupom.setIdCupom(1);
        cupom.setIdEmpresa(new Random().nextInt());
        cupom.setNomeCupom("Cupom promocional");
        cupom.setAtivo(TipoAtivo.S);
        cupom.setDescricao("Compra com desconto");
        cupom.setTaxaDesconto(BigDecimal.valueOf(10));

        return cupom;
    }

    public static CupomDTO retornarCupomDTO(Cupom cupom){
        CupomDTO cupomDTO = new CupomDTO();
        cupomDTO.setIdCupom(cupom.getIdCupom());
        cupomDTO.setIdEmpresa(cupom.getIdEmpresa());
        cupomDTO.setNomeCupom(cupom.getNomeCupom());
        cupomDTO.setAtivo(cupom.getAtivo());
        cupomDTO.setDescricao(cupom.getDescricao());
        cupomDTO.setTaxaDesconto(cupom.getTaxaDesconto());

        return cupomDTO;
    }

    public static CreateCupomDTO retornarCupomCreateDTO(Cupom cupom) {
        CreateCupomDTO cupomDTO = new CreateCupomDTO();
        cupomDTO.setNomeCupom(cupom.getNomeCupom());
        cupomDTO.setAtivo(cupom.getAtivo());
        cupomDTO.setDescricao(cupom.getDescricao());
        cupomDTO.setTaxaDesconto(cupom.getTaxaDesconto());

        return cupomDTO;
    }

    public static UpdateCupomDTO retornarCupomUpdateDTO(Cupom cupom) {
        UpdateCupomDTO cupomDTO = new UpdateCupomDTO();
        cupomDTO.setIdEmpresa(cupom.getIdEmpresa());
        cupomDTO.setNomeCupom(cupom.getNomeCupom());
        cupomDTO.setAtivo(cupom.getAtivo());
        cupomDTO.setDescricao(cupom.getDescricao());
        cupomDTO.setTaxaDesconto(cupom.getTaxaDesconto());

        return cupomDTO;
    }

    public static List<Cupom> retornarCupons(Cupom cupom) {
        return List.of(cupom, cupom, cupom);
    }

    public static List<CupomDTO> retornarCuponsDTO(CupomDTO cupomDTO) {
        return List.of(cupomDTO, cupomDTO, cupomDTO);
    }
}
