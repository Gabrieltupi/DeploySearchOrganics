package com.vemser.dbc.searchorganic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.controller.documentacao.ICupomController;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDto;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.service.CupomService;
import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/cupom")
@RestController
public class CupomController implements ICupomController {
    private final CupomService cupomService;
    private final ObjectMapper objectMapper;


    public CupomController(CupomService cupomService, ObjectMapper objectMapper) {
        this.cupomService = cupomService;
        this.objectMapper = objectMapper;
    }

    @Override
    @GetMapping("/{nomeCupom}/{idEmpresa}")
    public ResponseEntity<CupomDto> buscarPorNomeEEmpresa(
            @PathVariable("nomeCupom") String nomeCupom,
            @PathVariable("idEmpresa") int idEmpresa
    ) {
        Cupom cupom = cupomService.getCupomByNameAndEmpresa(nomeCupom, idEmpresa);

        if (cupom != null) {
            CupomDto cupomDto = new CupomDto();
            cupomDto.setNomeCupom(cupom.getNomeCupom());
            cupomDto.setAtivo(cupom.getAtivo());
            cupomDto.setDescricao(cupom.getDescricao());
            cupomDto.setTaxaDeDesconto(cupom.getTaxaDeDesconto());
            cupomDto.setIdEmpresa(cupom.getIdEmpresa());

            return new ResponseEntity<>(cupomDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}
