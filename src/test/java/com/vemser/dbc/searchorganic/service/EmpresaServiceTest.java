package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.CargoRepository;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import com.vemser.dbc.searchorganic.service.mocks.MockEmpresa;
import com.vemser.dbc.searchorganic.service.mocks.MockUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
            CreateEmpresaDTO createEmpresaDTO = MockEmpresa.retornarEmpresaCreateDTO();
            Empresa empresa = MockEmpresa.retornarEmpresa();
            Usuario usuario = MockUsuario.retornarUsuario();

            empresa.setIdUsuario(usuario.getIdUsuario());
            EmpresaDTO empresaDTO = MockEmpresa.retornarEmpresaDTO(empresa);
            Cargo cargoEmpresa = MockUsuario.obterCargo("ROLE_EMPRESA");

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
        Empresa empresa = MockEmpresa.retornarEmpresa();
        Usuario usuario = MockUsuario.retornarUsuario();
        usuario.getCargos().add(MockUsuario.obterCargo("ROLE_EMPRESA"));
        UpdateEmpresaDTO empresaUpdateDTO = MockEmpresa.obterEmpresaUpdate();

        empresa.setIdUsuario(usuario.getIdUsuario());

        empresaUpdateDTO.setIdUsuario(usuario.getIdUsuario());

        EmpresaDTO empresaAntigaDTO = MockEmpresa.retornarEmpresaDTO(empresa);

        EmpresaDTO empresaAtualizadaDTO = retornarEmpresaAtualizada(empresa, empresaUpdateDTO);


        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();

        when(empresaRepository.save(any())).thenReturn(empresa);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));
        when(objectMapper.convertValue(empresa, EmpresaDTO.class)).thenReturn(empresaAtualizadaDTO);
        when(usuarioService.getLoggedUser()).thenReturn(usuario);

        EmpresaDTO empresaAtualizada = empresaService.update(empresaUpdateDTO.getIdUsuario(), empresaUpdateDTO);

        Assertions.assertAll(
                () -> assertNotNull(empresaAtualizada),
                () -> assertNotEquals(empresaAtualizada, empresaAntigaDTO)
        );

    }

    @Test
    @DisplayName("Deveria atualizar uma empresa com sucesso caso seja admin")
    void atualizarEmpresaSendoAdmin() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        Usuario usuario = MockUsuario.retornarUsuario();
        usuario.getCargos().add(MockUsuario.obterCargo("ROLE_ADMIN"));
        UpdateEmpresaDTO empresaUpdateDTO = MockEmpresa.obterEmpresaUpdate();

        empresa.setIdUsuario(2);

        empresaUpdateDTO.setIdUsuario(3);

        EmpresaDTO empresaAntigaDTO = MockEmpresa.retornarEmpresaDTO(empresa);

        EmpresaDTO empresaAtualizadaDTO = retornarEmpresaAtualizada(empresa, empresaUpdateDTO);


        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();

        when(empresaRepository.existsAdminCargoByUserId(usuario.getIdUsuario())).thenReturn(1);
        when(empresaRepository.save(any())).thenReturn(empresa);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));
        when(objectMapper.convertValue(empresa, EmpresaDTO.class)).thenReturn(empresaAtualizadaDTO);
        when(usuarioService.getLoggedUser()).thenReturn(usuario);

        EmpresaDTO empresaAtualizada = empresaService.update(empresaUpdateDTO.getIdUsuario(), empresaUpdateDTO);

        Assertions.assertAll(
                () -> assertNotNull(empresaAtualizada),
                () -> assertNotEquals(empresaAtualizada, empresaAntigaDTO)
        );

    }

    @Test
    @DisplayName("Não deve atualizar uma empresa que não seja sua")
    void naoDeveAtualizarEmpresaQueNaoSejaSua() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        usuario.getCargos().add(MockUsuario.obterCargo("ROLE_EMPRESA"));
        UpdateEmpresaDTO empresaUpdateDTO = MockEmpresa.obterEmpresaUpdate();
        empresaUpdateDTO.setIdUsuario(0);
        usuario.setIdUsuario(1);

        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();
        when(usuarioService.getLoggedUser()).thenReturn(usuario);

        assertThrows(RegraDeNegocioException.class, () -> empresaService.update(usuario.getIdUsuario(), empresaUpdateDTO));
    }
    @Test
    @DisplayName("Não deve atualizar uma empresa caso não tenha role empresa")
    void naoDeveAtualizarEmpresaSemRole() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        UpdateEmpresaDTO empresaUpdateDTO = MockEmpresa.obterEmpresaUpdate();

        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();
        when(usuarioService.getLoggedUser()).thenReturn(usuario);

        assertThrows(RegraDeNegocioException.class, () -> empresaService.update(usuario.getIdUsuario(), empresaUpdateDTO));
    }
    @Test
    @DisplayName("Deve excluir uma empresa que seja sua")
    void deveExcluirEmpresa() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        Usuario usuario = MockUsuario.retornarUsuario();
        empresa.setIdUsuario(usuario.getIdUsuario());

        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));

        empresaService.delete(empresa.getIdEmpresa());

        verify(empresaRepository, times(1)).delete(empresa);
    }
    @Test
    @DisplayName("Deve excluir uma empresa caso seja admin")
    void deveExcluirEmpresaSendoAdmin() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        Usuario usuario = MockUsuario.retornarUsuario();
        usuario.getCargos().add(MockUsuario.obterCargo("ROLE_ADMIN"));
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
        Empresa empresa = MockEmpresa.retornarEmpresa();
        Usuario usuario = MockUsuario.retornarUsuario();
        empresa.setIdUsuario(new Random().nextInt());
        doReturn(usuario.getIdUsuario()).when(empresaService).getIdLoggedUser();

        when(empresaRepository.findById(anyInt())).thenReturn(Optional.of(empresa));

        assertThrows(RegraDeNegocioException.class, () ->  empresaService.delete(empresa.getIdEmpresa()));
    }

    @Test
    @DisplayName("Deve listar empresas")
    void deveListarEmpresas() throws Exception {
         Pageable pageable = PageRequest.of(0, 10);
         Page<Empresa> empresasMock = MockEmpresa.retornarEmpresasPageable();
         Empresa empresa = empresasMock.stream().findFirst().orElse(null);
         EmpresaDTO empresaDTO = new EmpresaDTO(empresa);

         when(empresaRepository.findAll(any(Pageable.class))).thenReturn(empresasMock);
         when(objectMapper.convertValue(empresa, EmpresaDTO.class)).thenReturn(empresaDTO);

         Page<EmpresaDTO> empresasDTOs = empresaService.findAll(pageable);

         Assertions.assertAll(
                 () -> assertNotNull(empresasDTOs),
                 () -> assertEquals(empresasDTOs.stream().findFirst().get().getIdEmpresa(), empresasMock.stream().findFirst().get().getIdEmpresa())
         );


    }
    @Test
    @DisplayName("Deve obter uma empresa dto pelo id")
    void deveObterEmpresaPeloId() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        EmpresaDTO empresaDTO = new EmpresaDTO(empresa);

        when(empresaRepository.findById(empresa.getIdEmpresa())).thenReturn(Optional.of(empresa));
        when(objectMapper.convertValue(empresa, EmpresaDTO.class)).thenReturn(empresaDTO);

        EmpresaDTO empresaRetornada = empresaService.findById(empresa.getIdEmpresa());

        Assertions.assertAll(
                () -> assertNotNull(empresaRetornada),
                () -> assertEquals(empresaRetornada, empresaDTO)
        );

    }

    @Test
    @DisplayName("Não deve obter uma empresaDTO que não existe")
    void naoDeveObterEmpresaDTONaoExiste() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        empresa.setIdEmpresa(2);
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.empty());

       assertThrows(RegraDeNegocioException.class, () ->  empresaService.findById(empresa.getIdEmpresa()));

    }
    @Test
    @DisplayName("Deve obter uma empresa")
    void deveObterEmpresa() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
         when(empresaRepository.findById(empresa.getIdEmpresa())).thenReturn(Optional.of(empresa));

        Empresa empresaRetornada = empresaService.getById(empresa.getIdEmpresa());

        Assertions.assertAll(
                () -> assertNotNull(empresaRetornada),
                () -> assertEquals(empresaRetornada, empresa)
        );

    }
    @Test
    @DisplayName("Não deve obter uma empresa que não existe")
    void naoDeveObterEmpresaNaoExisteId() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        when(empresaRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () ->  empresaService.getById(empresa.getIdEmpresa()));

    }

    @Test
    @DisplayName("Deve obter uma empresa com produtos pelo id")
    void deveObterEmpresaComProdutosPeloId() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        EmpresaProdutosDTO empresaProdutosDTO = MockEmpresa.retornarEmpresaProdutosDTO(empresa);

        when(empresaRepository.findByIdWithProdutos(empresa.getIdEmpresa())).thenReturn(Optional.of(empresa));
        when(objectMapper.convertValue(empresa, EmpresaProdutosDTO.class)).thenReturn(empresaProdutosDTO);

        EmpresaDTO empresaRetornada = empresaService.findByIdWithProdutos(empresa.getIdEmpresa());

        Assertions.assertAll(
                () -> assertNotNull(empresaRetornada),
                () -> assertEquals(empresaRetornada, empresaProdutosDTO)
        );

    }

    @Test
    @DisplayName("Não deve obter uma empresa  que não existe, com produtos")
    void naoDeveObterEmpresaNaoExisteComProdutos() throws Exception {
        Empresa empresa = MockEmpresa.retornarEmpresa();
        assertThrows(RegraDeNegocioException.class, () ->  empresaService.findByIdWithProdutos(empresa.getIdEmpresa()));

    }
    @Test
    @DisplayName("Deve empresas com produtos")
    void deveObterEmpresasComProdutosPeloId() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Empresa> empresasMock = MockEmpresa.retornarEmpresasPageable();
        Page<EmpresaProdutosDTO> empresaProdutosDTOSMock = MockEmpresa.retornarEmpresasComProdutos(empresasMock);

        Empresa empresa = empresasMock.stream().findFirst().get();
        EmpresaProdutosDTO empresaProdutosDTO = empresaProdutosDTOSMock.stream().findFirst().get();

        when(empresaRepository.findAllWithProdutos(any(Pageable.class))).thenReturn(empresasMock);
        when(objectMapper.convertValue(empresa, EmpresaProdutosDTO.class)).thenReturn(empresaProdutosDTO);

        Page<EmpresaProdutosDTO> empresasRetornada = empresaService.findAllWithProdutos(pageable);

        Assertions.assertAll(
                () -> assertTrue(empresasRetornada.hasContent()),
                () -> assertThat(empresasRetornada.getContent()).isEqualTo(empresaProdutosDTOSMock.getContent()),
                () -> assertEquals(empresasRetornada.getSize(), empresasMock.getSize())
        );

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