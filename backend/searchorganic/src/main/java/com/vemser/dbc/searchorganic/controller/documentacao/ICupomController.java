package com.vemser.dbc.searchorganic.controller.documentacao;

import com.vemser.dbc.searchorganic.dto.cupom.CupomDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ICupomController {

    @Operation(summary = "Mostrar cupons", description = "Mostrar cupom")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Mostrando cupom"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/{nomeCupom}/{idEmpresa}")
    public ResponseEntity<CupomDto> buscarPorNomeEEmpresa(
            @PathVariable("nomeCupom") String nomeCupom,
            @PathVariable("idEmpresa") int idEmpresa
    );
}
