package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.CargoRepository;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmpresaService - Test")
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private  CargoRepository cargoRepository;
    @InjectMocks
    private EmpresaService empresaService;
    @Mock
    private UsuarioService usuarioService;

    @Test
    @DisplayName("Deveria criar uma nova empresa com sucesso")
    void deveriaCriarEmpresaComSucesso() throws Exception {
            CreateEmpresaDTO createEmpresaDTO = Mocks.retornarEmpresaCreateDTO();
            Empresa empresa = Mocks.retornarEmpresa();
            Usuario usuario = Mocks.retornarUsuario();

            empresa.setIdUsuario(usuario.getIdUsuario());
            EmpresaDTO empresaDTO = Mocks.retornarEmpresaDTO(empresa);
            Cargo cargoEmpresa = Mocks.obterCargo("ROLE_EMPRESA");

            when(objectMapper.convertValue(createEmpresaDTO, Empresa.class)).thenReturn(empresa);
            when(empresaRepository.save(any())).thenReturn(empresa);
            when(objectMapper.convertValue(empresa, EmpresaDTO.class)).thenReturn(empresaDTO);
            when(usuarioService.obterUsuarioPorId(anyInt())).thenReturn(usuario);

            when(cargoRepository.findByNome("ROLE_EMPRESA")).thenReturn(cargoEmpresa);

            EmpresaDTO empresaDTOCriada = empresaService.save(usuario.getIdUsuario(), createEmpresaDTO);

             Assertions.assertAll(
                    () -> assertNotNull(empresaDTOCriada),
                    () -> assertEquals(empresaDTOCriada, empresaDTO),
                    () -> assertEquals(empresaDTOCriada.getIdUsuario(), empresa.getIdUsuario()),
                    () -> assertEquals(empresaDTOCriada.getIdEmpresa(), empresa.getIdEmpresa()),
                    () -> assertTrue(usuario.getCargos().contains(cargoEmpresa))
                     );
    }



}