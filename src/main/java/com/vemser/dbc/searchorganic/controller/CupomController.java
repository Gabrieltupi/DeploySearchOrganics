package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.documentacao.ICupomController;
import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import com.vemser.dbc.searchorganic.service.CupomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cupom")
@RequiredArgsConstructor
public class CupomController implements ICupomController {
    private final CupomService cupomService;

    @GetMapping
    public ResponseEntity<List<CupomDTO>> findAll() throws Exception {
        return new ResponseEntity<>(cupomService.findAll(), HttpStatus.OK);
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

    @DeleteMapping("/{idCupom}")
    public ResponseEntity<Void> delete(@PathVariable("idCupom") Integer idCupom) throws Exception {
        cupomService.delete(idCupom);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
