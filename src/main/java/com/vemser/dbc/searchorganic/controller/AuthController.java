package com.vemser.dbc.searchorganic.controller;



import com.vemser.dbc.searchorganic.dto.autenticacao.AuthToken;
import com.vemser.dbc.searchorganic.dto.autenticacao.UsuarioTokenDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioCreateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioLoginDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.security.TokenService;
import com.vemser.dbc.searchorganic.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AuthController {
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;
    public final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AuthToken> auth(@RequestBody @Valid UsuarioLoginDTO loginDTO) throws RegraDeNegocioException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getLogin(),
                        loginDTO.getSenha()
                );

        Authentication authentication =
                authenticationManager.authenticate(
                        usernamePasswordAuthenticationToken);

        Usuario usuarioValidado = (Usuario) authentication.getPrincipal();

        String token = tokenService.generateToken(usuarioValidado);
        AuthToken authToken = new AuthToken(token);

        return ResponseEntity.ok(authToken);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioTokenDTO> cadastrarUsuario(@RequestBody @Valid UsuarioCreateDTO usuarioDTO) throws Exception {
        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());

        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setSobrenome(usuarioDTO.getSobrenome());
        usuario.setDataNascimento(usuarioDTO.getDataNascimento());
        usuario.setCpf(usuarioDTO.getCpf());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setLogin(usuarioDTO.getLogin());
        usuario.setSenha(senhaCriptografada);
        usuario.setTipoAtivo(usuarioDTO.getTipoAtivo());

        Usuario novoUsuario = usuarioService.criarUsuario(usuario);

        UsuarioTokenDTO usuarioTokenDTO = new UsuarioTokenDTO();
        usuarioTokenDTO.setId(novoUsuario.getIdUsuario());
        usuarioTokenDTO.setLogin(novoUsuario.getLogin());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioTokenDTO);
    }

}
