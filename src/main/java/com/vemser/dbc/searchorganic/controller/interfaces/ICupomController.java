package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Cupom", description = "Endpoints de Cupom")
public interface ICupomController {
    @Operation(summary = "Listar cupons", description = "Listar todos os cupons")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todos os cupons"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<Page<CupomDTO>> findAll(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception;

    @Operation(summary = "Obter cupom", description = "Obter cupom por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cupom por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCupom}")
    ResponseEntity<CupomDTO> findById(@PathVariable("idCupom") Integer idCupom) throws Exception;

    @Operation(summary = "Criar cupom", description = "Criar cupom por empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cupom criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/empresa/{idEmpresa}")
    ResponseEntity<CupomDTO> save(@PathVariable("idEmpresa") Integer idEmpresa, @Valid @RequestBody CreateCupomDTO cupomDto) throws Exception;

    @Operation(summary = "Editar cupom", description = "Editar cupom por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cupom editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCupom}")
    ResponseEntity<CupomDTO> update(@PathVariable("idCupom") Integer idCupom, @RequestBody UpdateCupomDTO cupomDto) throws Exception;

    @Operation(summary = "Listar cupons por empresa", description = "Listar todos os cupons por empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna todos os cupons por empresa"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/empresa/{idEmpresa}")
    ResponseEntity<Page<CupomDTO>> findAllByIdEmpresa(@PathVariable("idEmpresa") Integer idEmpresa, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) throws Exception;
}
