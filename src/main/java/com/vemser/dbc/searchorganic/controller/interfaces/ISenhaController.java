package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.senha.RecuperarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.ResetarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.SenhaDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Senha", description = "Endpoints de Senha")
public interface ISenhaController {
    @Operation(summary = "Recuperar senha", description = "Recuperar a senha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/recuperar")
    ResponseEntity<SenhaDTO> recover(@RequestBody RecuperarSenhaDTO senhaDto) throws Exception;

    @Operation(summary = "Resetar senha", description = "Reseta a senha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna mensagem de sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/resetar")
    ResponseEntity<SenhaDTO> reset(@RequestBody ResetarSenhaDTO senhaDto) throws Exception;
}
