package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Empresa", description = "Endpoints de Empresa")
public interface IEmpresaController {
    @Operation(summary = "Listar empresas", description = "Listar todas as empresas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todas as empresas"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<Page<EmpresaDTO>> findAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception;

    @Operation(summary = "Obter empresa", description = "Obter empresa por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna empresa por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEmpresa}")
    ResponseEntity<EmpresaDTO> findById(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;

    @Operation(summary = "Criar empresa", description = "Criar empresa por usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna empresa criada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/usuario/{idUsuario}")
    ResponseEntity<EmpresaDTO> save(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody CreateEmpresaDTO empresaDto) throws Exception;

    @Operation(summary = "Editar empresa", description = "Editar empresa por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna empresa editada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEmpresa}")
    ResponseEntity<EmpresaDTO> update(@PathVariable("idEmpresa") Integer idEmpresa, @RequestBody UpdateEmpresaDTO empresaDto) throws Exception;

    @Operation(summary = "Remover empresa", description = "Remover empresa por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ""),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEmpresa}")
    ResponseEntity<Void> delete(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;

    @Operation(summary = "Listar empresas com produtos", description = "Listar todas as empresas com produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todas as empresas com produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/produtos")
    ResponseEntity<Page<EmpresaProdutosDTO>> findAllWithProdutos(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception;

    @Operation(summary = "Obter empresa com produtos", description = "Obter empresa por id com produtos")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna empresa por id com produtos"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEmpresa}/produtos")
    ResponseEntity<EmpresaProdutosDTO> findByIdWithProdutos(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;
}


