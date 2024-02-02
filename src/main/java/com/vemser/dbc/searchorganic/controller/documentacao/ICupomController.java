package com.vemser.dbc.searchorganic.controller.documentacao;

import com.vemser.dbc.searchorganic.dto.cupom.CreateCupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.cupom.UpdateCupomDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<List<CupomDTO>> findAll() throws Exception;

    @Operation(summary = "Obter cupom", description = "Obter cupom por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cupom por id"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{idCupom}")
    public ResponseEntity<CupomDTO> findById(@PathVariable("idCupom") Integer idCupom) throws Exception;

    @Operation(summary = "Criar cupom", description = "Criar cupom por empresa")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cupom criado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/empresa/{idEmpresa}")
    public ResponseEntity<CupomDTO> save(@PathVariable("idEmpresa") Integer idEmpresa, @Valid @RequestBody CreateCupomDTO cupomDto) throws Exception;

    @Operation(summary = "Editar cupom", description = "Editar cupom por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna cupom editado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/{idCupom}")
    public ResponseEntity<CupomDTO> update(@PathVariable("idCupom") Integer idCupom, @RequestBody UpdateCupomDTO cupomDto) throws Exception;

    @Operation(summary = "Remover cupom", description = "Remover cupom por id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = ""),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCupom}")
    public ResponseEntity<Void> delete(@PathVariable("idCupom") Integer idCupom) throws Exception;
}
