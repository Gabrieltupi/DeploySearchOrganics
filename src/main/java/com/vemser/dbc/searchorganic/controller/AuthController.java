package com.vemser.dbc.searchorganic.controller;


import com.vemser.dbc.searchorganic.controller.interfaces.IAuthController;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioLoginDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.security.TokenService;
import com.vemser.dbc.searchorganic.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Validated
@RequiredArgsConstructor
public class AuthController implements IAuthController {
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    @PostMapping
    public String auth(@RequestBody @Valid UsuarioLoginDTO loginDTO) throws RegraDeNegocioException {
        Optional<Usuario> byLoginAndSenha = usuarioService.findByLoginAndSenha(loginDTO.getLogin(), loginDTO.getSenha());
        if (byLoginAndSenha.isPresent()) {
            return tokenService.getToken(byLoginAndSenha.get());
        } else {
            throw new RegraDeNegocioException("Usuário ou senha inválidos");
        }
    }
}
