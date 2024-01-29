package com.vemser.dbc.searchorganic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.controller.documentacao.IEmpresaController;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDto;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Cupom;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.service.CupomService;
import com.vemser.dbc.searchorganic.service.EmpresaService;
import com.vemser.dbc.searchorganic.service.ProdutoService;
import com.vemser.dbc.searchorganic.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/empresa")
public class EmpresaController implements IEmpresaController {
    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final CupomService cupomService;
    private final ProdutoService produtoService;

    public EmpresaController(EmpresaService empresaService, UsuarioService usuarioService, ObjectMapper objectMapper, CupomService cupomService, ProdutoService produtoService){
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
        this.cupomService = cupomService;
        this.produtoService = produtoService;
    }

    @Override
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> exibirEmpresa(@PathVariable ("idEmpresa") Integer idEmpresa) throws Exception{
        Empresa EmpresaEntity = this.empresaService.buscarEmpresa(idEmpresa);
        EmpresaDTO empresaDTO = this.empresaService.preencherInformacoes(EmpresaEntity);
        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);

    }

    @Override
    @PostMapping("/{idUsuario}")
    public ResponseEntity<EmpresaDTO> criarEmpresa(@PathVariable("idUsuario") Integer idUsuario,
                                                   @Valid @RequestBody CreateEmpresaDTO empresa) throws Exception {
        usuarioService.obterUsuarioPorId(idUsuario);
        Empresa empresaEntity = objectMapper.convertValue(empresa, Empresa.class);
        empresaEntity.setIdEmpresa(idUsuario);
        Empresa empresaAdicionada = this.empresaService.adicionarEmpresa(empresaEntity);
        EmpresaDTO empresaDTO = objectMapper.convertValue(empresaAdicionada, EmpresaDTO.class);
        return new ResponseEntity<>(empresaDTO, HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> updateEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, @Valid @RequestBody UpdateEmpresaDTO novaEmpresa) throws Exception {
        Empresa empresaEntity = objectMapper.convertValue(novaEmpresa, Empresa.class);
        Empresa empresaAtualizada = empresaService.atualizarEmpresa(idEmpresa, empresaEntity);
        EmpresaDTO empresaDTO = objectMapper.convertValue(empresaAtualizada, EmpresaDTO.class);

        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable("idEmpresa") Integer idEmpresa)throws Exception{
        this.empresaService.excluirEmpresa(idEmpresa);
        return new  ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Produto>> listarProdutosDaLoja(Integer idLoja) throws Exception {
            List<Produto> produtos = empresaService.listarProdutosDaLoja(idLoja);
            return new ResponseEntity<>(produtos, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{idEmpresa}/cupom")
    public ResponseEntity<List<Cupom>> listarCupomDaLoja(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
        List<Cupom> cupoms = cupomService.listarCupomPorEmpresa(idEmpresa);
        return new ResponseEntity<>(cupoms, HttpStatus.OK);
    }


    @Override
    @PostMapping("/{idEmpresa}/cupom")
    public ResponseEntity<Void> adicionarCupom(@PathVariable("idEmpresa") Integer idEmpresa,
                                               @RequestBody Cupom cupom) throws Exception {
        cupomService.adicionarCupom(idEmpresa, cupom);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @GetMapping("/{idEmpresa}/cupons")
    public ResponseEntity<List<Cupom>> listarCuponsDaEmpresa(@PathVariable("idEmpresa") Integer idEmpresa) {
        try {
            List<Cupom> cupons = cupomService.listarCupomPorEmpresa(idEmpresa);
            return new ResponseEntity<>(cupons, HttpStatus.OK);
        } catch (BancoDeDadosException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @DeleteMapping("/cupom/{idCupom}")
    public ResponseEntity<Void> removerCupom(@PathVariable("idCupom") Integer idCupom) {
        cupomService.removerCupom(idCupom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @PutMapping("/cupom/{idCupom}")
    public ResponseEntity<CupomDto> atualizarCupom(@PathVariable("idCupom") Integer idCupom,
                                               @RequestBody Cupom cupom) throws Exception {
        cupomService.atualizarCupom(idCupom, cupom);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @GetMapping("/cupom")
    public ResponseEntity<List<Cupom>> listarCupom() throws Exception {
        List<Cupom> cupoms = cupomService.listarCupons();
        return new ResponseEntity<>(cupoms, HttpStatus.OK);
    }


}
