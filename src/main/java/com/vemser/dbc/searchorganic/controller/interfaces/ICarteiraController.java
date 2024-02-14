package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.carteira.CarteiraDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Carteira", description = "Endpoints de Carteira")
public interface ICarteiraController {
    @Operation(summary = "Obter saldo da carteira", description = "Obter saldo da carteira")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna saldo da carteira"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    ResponseEntity<CarteiraDTO> obterSaldo() throws Exception;
}
