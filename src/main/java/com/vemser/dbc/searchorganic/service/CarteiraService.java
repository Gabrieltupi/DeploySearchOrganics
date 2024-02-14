package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.carteira.CarteiraDTO;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.interfaces.ICarteiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarteiraService implements ICarteiraService {
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public CarteiraDTO obterSaldo() throws Exception {
        Usuario usuario = usuarioService.getLoggedUser();
        return objectMapper.convertValue(usuario.getCarteira(), CarteiraDTO.class);
    }
}


