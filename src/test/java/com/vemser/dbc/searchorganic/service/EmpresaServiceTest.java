package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.CargoRepository;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import org.hibernate.sql.Update;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EmpresaService - Test")
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private  CargoRepository cargoRepository;
    @Spy
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
                    () -> assertEquals(empresaDTOCriada.getIdUsuario(), empresa.getIdUsuario()),
                    () -> assertTrue(usuario.getCargos().contains(cargoEmpresa))
                     );
    }

    @Test
    @DisplayName("Deveria atualizar uma empresa com sucesso")
    void atualizarEmpresaComSucesso() throws Exception {
        Empresa empresa = Mocks.retornarEmpresa();
        Usuario usuario = Mocks.retornarUsuario();
        usuario.getCargos().add(Mocks.obterCargo("ROLE_EMPRESA"));
        UpdateEmpresaDTO empresaUpdateDTO = Mocks.obterEmpresaUpdate();

        empresa.setIdUsuario(usuario.getIdUsuario());

        empresaUpdateDTO.setIdUsuario(usuario.getIdUsuario());

        EmpresaDTO empresaAntigaDTO = Mocks.retornarEmpresaDTO(empresa);

        EmpresaDTO empresaAtualizadaDTO = retornarEmpresaAtualizada(empresa, empresaUpdateDTO);


        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();

        when(empresaRepository.save(any())).thenReturn(empresa);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));
        when(objectMapper.convertValue(empresa, EmpresaDTO.class)).thenReturn(empresaAtualizadaDTO);
        when(usuarioService.findById(anyInt())).thenReturn(usuario);

        EmpresaDTO empresaAtualizada = empresaService.update(empresaUpdateDTO.getIdUsuario(), empresaUpdateDTO);

        Assertions.assertAll(
                () -> assertNotNull(empresaAtualizada),
                () -> assertNotEquals(empresaAtualizada, empresaAntigaDTO)
        );

    }

    @Test
    @DisplayName("Deveria atualizar uma empresa com sucesso caso seja admin")
    void atualizarEmpresaSendoAdmin() throws Exception {
        Empresa empresa = Mocks.retornarEmpresa();
        Usuario usuario = Mocks.retornarUsuario();
        usuario.getCargos().add(Mocks.obterCargo("ROLE_ADMIN"));
        UpdateEmpresaDTO empresaUpdateDTO = Mocks.obterEmpresaUpdate();

        empresa.setIdUsuario(2);

        empresaUpdateDTO.setIdUsuario(3);

        EmpresaDTO empresaAntigaDTO = Mocks.retornarEmpresaDTO(empresa);

        EmpresaDTO empresaAtualizadaDTO = retornarEmpresaAtualizada(empresa, empresaUpdateDTO);


        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();

        when(empresaRepository.existsAdminCargoByUserId(usuario.getIdUsuario())).thenReturn(1);
        when(empresaRepository.save(any())).thenReturn(empresa);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));
        when(objectMapper.convertValue(empresa, EmpresaDTO.class)).thenReturn(empresaAtualizadaDTO);
        when(usuarioService.findById(anyInt())).thenReturn(usuario);

        EmpresaDTO empresaAtualizada = empresaService.update(empresaUpdateDTO.getIdUsuario(), empresaUpdateDTO);

        Assertions.assertAll(
                () -> assertNotNull(empresaAtualizada),
                () -> assertNotEquals(empresaAtualizada, empresaAntigaDTO)
        );

    }

    @Test
    @DisplayName("Não deve atualizar uma empresa que não seja sua")
    void naoDeveAtualizarEmpresaQueNaoSejaSua() throws Exception {
        Usuario usuario = Mocks.retornarUsuario();
        usuario.getCargos().add(Mocks.obterCargo("ROLE_EMPRESA"));
        UpdateEmpresaDTO empresaUpdateDTO = Mocks.obterEmpresaUpdate();
        empresaUpdateDTO.setIdUsuario(0);
        usuario.setIdUsuario(1);

        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();
        when(usuarioService.findById(anyInt())).thenReturn(usuario);
        assertThrows(RegraDeNegocioException.class, () -> empresaService.update(usuario.getIdUsuario(), empresaUpdateDTO));
    }
    @Test
    @DisplayName("Não deve atualizar uma empresa caso não tenha role empresa")
    void naoDeveAtualizarEmpresaSemRole() throws Exception {
        Usuario usuario = Mocks.retornarUsuario();
        UpdateEmpresaDTO empresaUpdateDTO = Mocks.obterEmpresaUpdate();

        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();
        when(usuarioService.findById(anyInt())).thenReturn(usuario);
        assertThrows(RegraDeNegocioException.class, () -> empresaService.update(usuario.getIdUsuario(), empresaUpdateDTO));
    }
    @Test
    @DisplayName("Deve excluir uma empresa que seja sua")
    void deveExcluirEmpresa() throws Exception {
        Empresa empresa = Mocks.retornarEmpresa();
        Usuario usuario = Mocks.retornarUsuario();
        empresa.setIdUsuario(usuario.getIdUsuario());

        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));

        empresaService.delete(empresa.getIdEmpresa());

        verify(empresaRepository, times(1)).delete(empresa);
    }
    @Test
    @DisplayName("Deve excluir uma empresa caso seja admin")
    void deveExcluirEmpresaSendoAdmin() throws Exception {
        Empresa empresa = Mocks.retornarEmpresa();
        Usuario usuario = Mocks.retornarUsuario();
        usuario.getCargos().add(Mocks.obterCargo("ROLE_ADMIN"));
        empresa.setIdUsuario(new Random().nextInt());

        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();

        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));
        when(empresaRepository.existsAdminCargoByUserId(usuario.getIdUsuario())).thenReturn(1);

        empresaService.delete(empresa.getIdEmpresa());

        verify(empresaRepository, times(1)).delete(empresa);
    }

    @Test
    @DisplayName("Não deve excluir uma empresa que não seja sua")
    void naoDeveExcluirEmpresa() throws Exception {
        Empresa empresa = Mocks.retornarEmpresa();
        Usuario usuario = Mocks.retornarUsuario();
        empresa.setIdUsuario(new Random().nextInt());
        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();

        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));

        assertThrows(RegraDeNegocioException.class, () ->  empresaService.delete(empresa.getIdEmpresa()));
    }

    private EmpresaDTO retornarEmpresaAtualizada(Empresa empresaParam, UpdateEmpresaDTO empresaUpdateDTO) {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(empresaParam.getIdEmpresa());
        empresa.setIdUsuario(empresaParam.getIdUsuario());
        empresa.setRazaoSocial(empresaUpdateDTO.getRazaoSocial());
        empresa.setCnpj(empresaUpdateDTO.getCnpj());
        empresa.setSetor(empresaUpdateDTO.getSetor());
        empresa.setNomeFantasia(empresaUpdateDTO.getNomeFantasia());
        empresa.setProdutos(empresaParam.getProdutos());
        empresa.setInscricaoEstadual(empresaUpdateDTO.getInscricaoEstadual());

        return new EmpresaDTO(empresa);
    }


}