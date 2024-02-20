package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.dto.senha.RecuperarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.ResetarSenhaDTO;
import com.vemser.dbc.searchorganic.dto.senha.SenhaDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import com.vemser.dbc.searchorganic.service.mocks.MockUsuario;
import com.vemser.dbc.searchorganic.utils.SenhaUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SenhaService - Test")
class SenhaServiceTest {
    @Mock
    private  UsuarioService usuarioService;
    @Mock
    private  EmailService emailService;
    @Mock
    private SenhaUtil senhaUtil;
    @Mock
    private  PasswordEncoder passwordEncoder;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Spy
    @InjectMocks
    private SenhaService senhaService;

    @Test
    @DisplayName("Deve recuperar a senha com dados válidos")
    void deveRecuperarSenha() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        RecuperarSenhaDTO recuperarSenhaDTO = new RecuperarSenhaDTO(usuario.getEmail(), usuario.getLogin());
        when(passwordEncoder.encode(anyString())).thenReturn("senhaCripto");
        when(senhaUtil.gerarSenha(anyInt())).thenReturn("novaSenha");
        when(usuarioService.findByLoginAndEmail(anyString(), anyString())).thenReturn(usuario);

        SenhaDTO senhaDTO = senhaService.recover(recuperarSenhaDTO);

        verify(usuarioService, times(1)).salvarUsuario(usuario);
        verify(senhaService, times(1)).sendRecoverEmail(usuario, "novaSenha");
        assertNotNull(senhaDTO);
    }

    @Test
    @DisplayName("Não deve recuperar a senha com dados inválidos")
    void naoDeveRecuperarSenha() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        RecuperarSenhaDTO recuperarSenhaDTO = new RecuperarSenhaDTO("dado", usuario.getLogin());

        assertThrows(RegraDeNegocioException.class, () -> senhaService.recover(recuperarSenhaDTO));
    }
    @Test
    @DisplayName("Deve conseguir resetar a senha com dados válidos")
    void deveResetarSenha() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        ResetarSenhaDTO resetarSenhaDTO = new ResetarSenhaDTO("senhaAtual", "novaSenha");

        when(passwordEncoder.encode(anyString())).thenReturn("senhaCripto");
        when(usuarioService.getLoggedUser()).thenReturn(usuario);
        when(usuarioService.findByLoginAndSenha(usuario.getLogin(), "senhaCripto")).thenReturn(Optional.of(usuario));

        SenhaDTO senhaDTO = senhaService.reset(resetarSenhaDTO);

        verify(usuarioService, times(1)).salvarUsuario(usuario);
        verify(senhaService, times(1)).sendResetEmail(usuario);
        assertEquals(usuario.getSenha(), "senhaCripto");
        assertNotNull(senhaDTO);
    }

    @Test
    @DisplayName("Não deve resetar a senha com dados inválidos")
    void naoDeveResetarSenha() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        ResetarSenhaDTO resetarSenhaDTO = new ResetarSenhaDTO("senhaAtual", "novaSenha");
        when(usuarioService.getLoggedUser()).thenReturn(usuario);
        when(usuarioService.findByLoginAndSenha(anyString(), anyString())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> senhaService.reset(resetarSenhaDTO));
    }


}