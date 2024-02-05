package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.ICupomController;
import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import com.vemser.dbc.searchorganic.service.CupomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cupom")
@RequiredArgsConstructor
public class CupomController implements ICupomController {
    private final CupomService cupomService;

    @GetMapping
    public ResponseEntity<Page<CupomDTO>> findAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(cupomService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{idCupom}")
    public ResponseEntity<CupomDTO> findById(@PathVariable("idCupom") Integer idCupom) throws Exception {
        return new ResponseEntity<>(cupomService.findById(idCupom), HttpStatus.OK);
    }

    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<CupomDTO> save(@PathVariable("idEmpresa") Integer idEmpresa, @Valid @RequestBody CreateCupomDTO cupomDto) throws Exception {
        return new ResponseEntity<>(cupomService.save(idEmpresa, cupomDto), HttpStatus.CREATED);
    }

    @PutMapping("/{idCupom}")
    public ResponseEntity<CupomDTO> update(@PathVariable("idCupom") Integer idCupom, @RequestBody UpdateCupomDTO cupomDto) throws Exception {
        return new ResponseEntity<>(cupomService.update(idCupom, cupomDto), HttpStatus.OK);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<Page<CupomDTO>> findAllByIdEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(cupomService.findAllByIdEmpresa(idEmpresa, pageable), HttpStatus.CREATED);
    }
}
