package com.vemser.dbc.searchorganic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
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
public class EmpresaController {
    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;
    private final ProdutoService produtoService;

    public EmpresaController(EmpresaService empresaService, UsuarioService usuarioService, ObjectMapper objectMapper, ProdutoService produtoService){
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
        this.produtoService = produtoService;
    }

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> exibirEmpresa(@PathVariable ("idEmpresa") Integer idEmpresa) throws Exception{
        Empresa EmpresaEntity = this.empresaService.buscarEmpresa(idEmpresa);
        EmpresaDTO empresaDTO = this.empresaService.preencherInformacoes(EmpresaEntity);
        return new ResponseEntity<>(empresaDTO, HttpStatus.OK);

    }

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

    @PutMapping("/{idUsuario}")
    public ResponseEntity<EmpresaDTO> updateEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, @PathVariable @RequestBody UpdateEmpresaDTO empresaAtualizada) throws Exception{
    Empresa empresa = this.empresaService.atualizarEmpresa(idEmpresa,empresaAtualizada);
    EmpresaDTO empresaDTO = this.empresaService.preencherInformacoes(empresa);
    return new ResponseEntity<>(empresaDTO,HttpStatus.OK);
    }

    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> deletarEmpresa(@PathVariable("idEmpresa") Integer idEmpresa)throws Exception{
        this.empresaService.excluirEmpresa(idEmpresa);
        return new  ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<List<Produto>> listarProdutosDaLoja(Integer idLoja) throws Exception {
            List<Produto> produtos = empresaService.listarProdutosDaLoja(idLoja);
            return new ResponseEntity<>(produtos, HttpStatus.OK);
    }
}
