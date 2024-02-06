package com.vemser.dbc.searchorganic.security;


import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    static final String HEADER_STRING = "Authorization";
    private final UsuarioService usuarioService;

    public String getToken(Usuario usuarioEntity) {
        String tokenTexto = usuarioEntity.getLogin() + ";" + usuarioEntity.getSenha();
        String token = Base64.getEncoder().encodeToString(tokenTexto.getBytes());
        return token;
    }
    // token = yeAGieha9eH(E8 = rafa;123
    public Optional<Usuario> isValid(String token) {
        if(token == null){
            return Optional.empty();
        }
        byte[] decodedBytes = Base64.getUrlDecoder().decode(token);
        String decoded = new String(decodedBytes);
        String[] split = decoded.split(";");
        return usuarioService.findByLoginAndSenha(split[0], split[1]);
    }
}
