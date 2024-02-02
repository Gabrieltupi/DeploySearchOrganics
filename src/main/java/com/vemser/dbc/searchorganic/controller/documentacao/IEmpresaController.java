package com.vemser.dbc.searchorganic.controller.documentacao;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<List<EmpresaDTO>> findAll() throws Exception;

    @Operation(summary = "Obter empresa", description = "Obter empresa por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna empresa por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> findById(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;

    @Operation(summary = "Criar empresa", description = "Criar empresa por usuário")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna empresa criada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/usuario/{idUsuario}")
    public ResponseEntity<EmpresaDTO> save(@PathVariable("idUsuario") Integer idUsuario, @Valid @RequestBody CreateEmpresaDTO empresaDto) throws Exception;

    @Operation(summary = "Editar empresa", description = "Editar empresa por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna empresa editada"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaDTO> update(@PathVariable("idEmpresa") Integer idEmpresa, @RequestBody UpdateEmpresaDTO empresaDto) throws Exception;

    @Operation(summary = "Remover empresa", description = "Remover empresa por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ""),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> delete(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception;
}


