package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.repository.EnderecoRepository;
import com.vemser.dbc.searchorganic.service.mocks.MockEndereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EnderecoService - Test")
class EnderecoServiceTest {
    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    EnderecoRepository enderecoRepository;

    @Mock
    private ObjectMapper objectMapper;

    private final Endereco enderecoMock = MockEndereco.retornarEndereco();
    private final EnderecoDTO enderecoDTOMock = MockEndereco.retornarEnderecoDTO(enderecoMock);
    private final EnderecoCreateDTO enderecoCreateDTOMock = MockEndereco.retornarEnderecoCreateDTO(enderecoMock);
    private final EnderecoUpdateDTO enderecoUpdateDTOMock = MockEndereco.retornarEnderecoUpdateDTO(enderecoMock);
    private final List<Endereco> enderecosMock = MockEndereco.retornarEnderecos(enderecoMock);
    private final List<EnderecoDTO> enderecosDTOMock = MockEndereco.retornarEnderecosDTO(enderecoDTOMock);


    @Test
    @DisplayName("Deveria retornar um endereço por id com sucesso")
    public void deveriaRetornarEnderecoDTOPorId() throws Exception {
        EnderecoDTO enderecoDTOMock = new EnderecoDTO(enderecoMock);

        when(usuarioService.getIdLoggedUser()).thenReturn(1);
        when(usuarioService.isAdmin()).thenReturn(true);
        when(enderecoRepository.findById(enderecoMock.getIdEndereco())).thenReturn(Optional.of(enderecoMock));

        EnderecoDTO enderecoRetornado = enderecoService.buscarEndereco(enderecoMock.getIdEndereco());

        assertNotNull(enderecoRetornado);
        assertEquals(enderecoDTOMock, enderecoRetornado);
    }

    @Test
    @DisplayName("Não deveria retornar um endereço por id de outro usuário")
    public void naoDeveriaRetornarEnderecoDTODeOutroUsuario() {
        when(usuarioService.getIdLoggedUser()).thenReturn(1);
        when(usuarioService.isAdmin()).thenReturn(false);
        when(enderecoRepository.findById(enderecoMock.getIdEndereco())).thenReturn(Optional.of(enderecoMock));

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.buscarEndereco(enderecoMock.getIdEndereco()));
    }

    @Test
    @DisplayName("Deveria listar endereços com sucesso")
    public void deveriaListarEnderecosComSucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Endereco> listaMock = new PageImpl<>(enderecosMock, pageable, enderecosMock.size());

        when(objectMapper.convertValue(any(Endereco.class), eq(EnderecoDTO.class))).thenReturn(enderecoDTOMock);
        when(enderecoRepository.findAll(pageable)).thenReturn(listaMock);

        Page<EnderecoDTO> listaDTORetornada = enderecoService.listarEnderecosPaginados(pageable);

        assertNotNull(listaDTORetornada);
        assertEquals(enderecosDTOMock.size(), listaDTORetornada.getContent().size());
        assertIterableEquals(enderecosDTOMock, listaDTORetornada.getContent());
    }

    @Test
    @DisplayName("Deveria criar um endereço com sucesso")
    public void deveriaCriarEndereco() throws Exception {
        when(objectMapper.convertValue(enderecoCreateDTOMock, Endereco.class)).thenReturn(enderecoMock);
        when(enderecoRepository.save(any())).thenReturn(enderecoMock);
        when(objectMapper.convertValue(enderecoMock, EnderecoDTO.class)).thenReturn(enderecoDTOMock);

        EnderecoDTO enderecoCriado =  enderecoService.adicionarEndereco(enderecoCreateDTOMock);

        assertNotNull(enderecoCriado);
        assertEquals(enderecoDTOMock, enderecoCriado);
    }

    @Test
    @DisplayName("Não deveria criar um endereço com cep inválido")
    public void naoDeveriaCriarEnderecoComCepInvalido() {
        enderecoCreateDTOMock.setCep("98765-432");

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.adicionarEndereco(enderecoCreateDTOMock));
    }

    @Test
    @DisplayName("Deveria atualizar um endereço com sucesso")
    public void deveriaAtualizarEndereco() throws Exception {
        enderecoUpdateDTOMock.setLogradouro("Novo Endereco");

        EnderecoDTO enderecoDTO = new EnderecoDTO(enderecoMock);

        when(enderecoRepository.findById(enderecoMock.getIdEndereco())).thenReturn(Optional.of(enderecoMock));
        when(enderecoRepository.save(any())).thenReturn(enderecoMock);

        EnderecoDTO enderecoCriado =  enderecoService.editarEndereco(enderecoMock.getIdEndereco(), enderecoUpdateDTOMock);

        assertNotNull(enderecoCriado);
        assertNotEquals(enderecoDTO, enderecoCriado);
        assertNotEquals(enderecoDTO.getLogradouro(), enderecoCriado.getLogradouro());
    }

    @Test
    @DisplayName("Não deveria atualizar um endereço inexistente")
    public void naoDeveriaAtualizarEnderecoInexistente() {
        when(enderecoRepository.findById(enderecoMock.getIdEndereco())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.editarEndereco(enderecoMock.getIdEndereco(), enderecoUpdateDTOMock));
    }

    @Test
    @DisplayName("Deveria remover um endereço com sucesso")
    public void deveriaRemoverEnderecoComSucesso() throws Exception {
        when(enderecoRepository.findById(enderecoMock.getIdEndereco())).thenReturn(Optional.of(enderecoMock));

        enderecoService.removerEndereco(enderecoMock.getIdEndereco());

        verify(enderecoRepository, times(1)).deleteById(enderecoMock.getIdEndereco());
    }

    @Test
    @DisplayName("Deveria listar endereços por usuário com sucesso")
    public void deveriaListarEnderecosPorUsuarioComSucesso() throws Exception {
        when(usuarioService.getIdLoggedUser()).thenReturn(enderecoMock.getUsuario().getIdUsuario());
        when(enderecoRepository.findAllByUsuarioIdUsuario(enderecoMock.getUsuario().getIdUsuario())).thenReturn(enderecosMock);

        List<EnderecoDTO> listaDTORetornada = enderecoService.listarEnderecosPorUsuario(enderecoMock.getUsuario().getIdUsuario());

        assertNotNull(listaDTORetornada);
        assertEquals(enderecosDTOMock.size(), listaDTORetornada.size());
        assertIterableEquals(enderecosDTOMock, listaDTORetornada);
    }

    @Test
    @DisplayName("Não deveria listar endereços por usuário de outro usuário")
    public void naoDeveriaListarEnderecosPorUsuarioDeOutroUsuario() {
        when(usuarioService.getIdLoggedUser()).thenReturn(1);

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.listarEnderecosPorUsuario(enderecoMock.getUsuario().getIdUsuario()));
    }

    @Test
    @DisplayName("Deveria gerar mensagem de email")
    public void deveriaGerarMensagemDeEmail() {
        String mensagemMock = String.format("""
                        Logradouro: %s  <br>
                        Número: %s       <br>
                        Complemento: %s   <br>
                        CEP: %s           <br>
                        Cidade: %s        <br>
                        Estado: %s        <br>
                        País: %s           <br>
                        """,
                enderecoMock.getLogradouro(),
                enderecoMock.getNumero(),
                enderecoMock.getComplemento(),
                enderecoMock.getCep(),
                enderecoMock.getCidade(),
                enderecoMock.getEstado(),
                enderecoMock.getPais()
        );

        String mensagemRetornada = enderecoService.getMensagemEnderecoEmail(enderecoMock);

        assertNotNull(mensagemRetornada);
        assertEquals(mensagemMock, mensagemRetornada);
    }
}
