package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.carteira.CarteiraDTO;
import com.vemser.dbc.searchorganic.model.Carteira;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.mocks.MockCarteira;
import com.vemser.dbc.searchorganic.service.mocks.MockUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Carteira Service- Test")
class CarteiraServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private CarteiraService carteiraService;

    @Test
    @DisplayName("retornaSucessoAoConsultarSaldo")
    public void retornaSucessoAoConsultarSaldo() throws Exception {
        Usuario usuario= MockUsuario.retornarUsuario();
        CarteiraDTO carteiraDTO= MockCarteira.retornaCarteiraDto();
        Carteira carteira= MockCarteira.retornarCarteira();
        usuario.setCarteira(carteira);

        when(usuarioService.getLoggedUser()).thenReturn(usuario);
        when(objectMapper.convertValue(usuario.getCarteira(), CarteiraDTO.class)).thenReturn(carteiraDTO);

        CarteiraDTO saldo= carteiraService.obterSaldo();

        assertEquals(carteiraDTO.getIdCarteira(), saldo.getIdCarteira());
        assertEquals(carteiraDTO.getSaldo(), saldo.getSaldo());

    }

}