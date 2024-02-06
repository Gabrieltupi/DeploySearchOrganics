package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.usuario.UsuarioLoginDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Tag(name = "Autenticacao de Login", description = "Endpoint de Autenticacao")
public interface IAuthController {

    @Operation(summary = "Autenticação", description = "Gera a chave do usuario!")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a chave de acesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public String auth(@RequestBody @Valid UsuarioLoginDTO loginDTO) throws RegraDeNegocioException;
}
