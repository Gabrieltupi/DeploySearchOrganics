package com.vemser.dbc.searchorganic.controller.interfaces;

import com.vemser.dbc.searchorganic.dto.autenticacao.AuthToken;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioCreateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioLoginDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/login")
    public ResponseEntity<AuthToken> auth(@RequestBody @Valid UsuarioLoginDTO loginDTO) throws RegraDeNegocioException;

    @Operation(summary = "Cadastro de usuario!", description = "Gera o cadastro do usuario!")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Retorna a confirmação!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody @Valid UsuarioCreateDTO usuarioDTO) throws Exception;
}
