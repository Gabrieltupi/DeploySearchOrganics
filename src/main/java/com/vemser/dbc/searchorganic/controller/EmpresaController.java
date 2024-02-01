package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaDTO>> findAll() throws Exception {
        return new ResponseEntity<>(empresaService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> findById(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        return new ResponseEntity<>(empresaService.findById(idEmpresa), HttpStatus.OK);
    }

    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<EmpresaDTO> save(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody CreateEmpresaDTO empresaDto) throws Exception {
        return new ResponseEntity<>(empresaService.save(idUsuario, empresaDto), HttpStatus.CREATED);
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> update(@PathVariable("idEmpresa") Integer idEmpresa, @RequestBody UpdateEmpresaDTO empresaDto) throws Exception {
        return new ResponseEntity<>(empresaService.update(idEmpresa, empresaDto), HttpStatus.OK);
    }

    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> delete(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        empresaService.delete(idEmpresa);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    public ResponseEntity<List<Produto>> listarProdutosDaLoja(Integer idLoja) throws Exception {
//        List<Produto> produtos = empresaService.listarProdutosDaLoja(idLoja);
//        return new ResponseEntity<>(produtos, HttpStatus.OK);
//    }
//
//    @GetMapping("/{idEmpresa}/cupom")
//    public ResponseEntity<List<Cupom>> listarCupomDaLoja(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
//        List<Cupom> cupoms = cupomService.listarCupomPorEmpresa(idEmpresa);
//        return new ResponseEntity<>(cupoms, HttpStatus.OK);
//    }
//
//
//    @PostMapping("/{idEmpresa}/cupom")
//    public ResponseEntity<Void> adicionarCupom(@PathVariable("idEmpresa") Integer idEmpresa,
//                                               @RequestBody Cupom cupom) throws Exception {
//        cupomService.adicionarCupom(idEmpresa, cupom);
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{idEmpresa}/cupons")
//    public ResponseEntity<List<Cupom>> listarCuponsDaEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) {
//        try {
//            List<Cupom> cupons = cupomService.listarCupomPorEmpresa(idEmpresa);
//            return new ResponseEntity<>(cupons, HttpStatus.OK);
//        } catch (BancoDeDadosException e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping("/cupom/{idCupom}")
//    public ResponseEntity<Void> removerCupom(@PathVariable("idCupom") Integer idCupom) {
//        cupomService.removerCupom(idCupom);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PutMapping("/cupom/{idCupom}/{idEmpresa}")
//    public ResponseEntity<CupomDto> atualizarCupom(@PathVariable("idCupom") Integer idCupom, @PathVariable("idEmpresa") Integer idEmpresa,
//                                                   @RequestBody Cupom cupom) throws Exception {
//        cupomService.atualizarCupom(idEmpresa, idCupom, cupom);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @GetMapping("/cupom")
//    public ResponseEntity<List<Cupom>> listarCupom() throws Exception {
//        List<Cupom> cupoms = cupomService.listarCupons();
//        return new ResponseEntity<>(cupoms, HttpStatus.OK);
//    }
}
