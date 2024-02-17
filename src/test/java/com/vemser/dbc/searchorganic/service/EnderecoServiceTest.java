package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.repository.EnderecoRepository;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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

    @Test
    public void deveriaRetornarEnderecoDTOPorId() throws Exception {
        Endereco enderecoMock = retornarEndereco();
        EnderecoDTO enderecoDTOMock = new EnderecoDTO(enderecoMock);

        when(usuarioService.getIdLoggedUser()).thenReturn(1);
        when(usuarioService.isAdmin()).thenReturn(true);
        when(enderecoRepository.findById(enderecoMock.getIdEndereco())).thenReturn(Optional.of(enderecoMock));

        EnderecoDTO enderecoRetornado = enderecoService.buscarEndereco(enderecoMock.getIdEndereco());

        assertNotNull(enderecoRetornado);
        assertEquals(enderecoDTOMock, enderecoRetornado);
    }

    @Test
    public void naoDeveriaRetornarEnderecoDTODeOutroUsuario() {
        Endereco enderecoMock = retornarEndereco();

        when(usuarioService.getIdLoggedUser()).thenReturn(1);
        when(usuarioService.isAdmin()).thenReturn(false);
        when(enderecoRepository.findById(enderecoMock.getIdEndereco())).thenReturn(Optional.of(enderecoMock));

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.buscarEndereco(enderecoMock.getIdEndereco()));
    }

    @Test
    public void deveriaListarEnderecosComSucesso() {
        Endereco endereco = retornarEndereco();
        EnderecoDTO enderecoDTO = retornarEnderecoDTO(endereco);
        List<Endereco> enderecosMock = List.of(endereco, endereco, endereco);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Endereco> listaMock = new PageImpl<>(enderecosMock, pageable, enderecosMock.size());
        List<EnderecoDTO> enderecosDTOMock = List.of(enderecoDTO, enderecoDTO, enderecoDTO);

        when(objectMapper.convertValue(any(Endereco.class), eq(EnderecoDTO.class))).thenReturn(enderecoDTO);
        when(enderecoRepository.findAll(pageable)).thenReturn(listaMock);

        Page<EnderecoDTO> listaDTORetornada = enderecoService.listarEnderecosPaginados(pageable);

        assertNotNull(listaDTORetornada);
        assertEquals(enderecosDTOMock.size(), listaDTORetornada.getContent().size());
        assertIterableEquals(enderecosDTOMock, listaDTORetornada.getContent());
    }

    @Test
    public void deveriaRetornarEnderecoPorId() throws Exception {
        Integer idAleatorio = new Random().nextInt();

        Endereco enderecoMock = retornarEndereco();

        when(enderecoRepository.findById(anyInt())).thenReturn(Optional.of(enderecoMock));

        Endereco enderecoRetornado = enderecoService.getById(idAleatorio);

        assertNotNull(enderecoRetornado);
        assertEquals(enderecoMock, enderecoRetornado);
    }

    @Test
    public void deveriaCriarEndereco() throws Exception {
        EnderecoCreateDTO enderecoCreateDTO = retornarEnderecoCreateDTO();
        Endereco endereco = retornarEndereco();
        EnderecoDTO enderecoDTO = retornarEnderecoDTO(endereco);

        when(objectMapper.convertValue(enderecoCreateDTO, Endereco.class)).thenReturn(endereco);
        when(enderecoRepository.save(any())).thenReturn(endereco);
        when(objectMapper.convertValue(endereco, EnderecoDTO.class)).thenReturn(enderecoDTO);

        EnderecoDTO enderecoCriado =  enderecoService.adicionarEndereco(enderecoCreateDTO);

        assertNotNull(enderecoCriado);
        assertEquals(enderecoDTO, enderecoCriado);
    }

    @Test
    public void naoDeveriaCriarEnderecoComCepInvalido() throws Exception {
        EnderecoCreateDTO enderecoCreateDTO = retornarEnderecoCreateDTO();
        enderecoCreateDTO.setCep("98765-432");

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.adicionarEndereco(enderecoCreateDTO));
    }

    @Test
    public void deveriaAtualizarEndereco() throws Exception {
        Endereco endereco = retornarEndereco();
        EnderecoUpdateDTO enderecoUpdateDTO = retornarEnderecoUpdateDTO(endereco);
        enderecoUpdateDTO.setLogradouro("Novo Endereco");

        EnderecoDTO enderecoDTO = new EnderecoDTO(endereco);

        when(enderecoRepository.findById(endereco.getIdEndereco())).thenReturn(Optional.of(endereco));
        when(enderecoRepository.save(any())).thenReturn(endereco);

        EnderecoDTO enderecoCriado =  enderecoService.editarEndereco(endereco.getIdEndereco(), enderecoUpdateDTO);

        assertNotNull(enderecoCriado);
        assertNotEquals(enderecoDTO, enderecoCriado);
        assertNotEquals(enderecoDTO.getLogradouro(), enderecoCriado.getLogradouro());
    }

    @Test
    public void naoDeveriaAtualizarEnderecoInexistente() {
        Endereco endereco = retornarEndereco();
        EnderecoUpdateDTO enderecoUpdateDTO = retornarEnderecoUpdateDTO(endereco);

        when(enderecoRepository.findById(endereco.getIdEndereco())).thenReturn(Optional.empty());

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.editarEndereco(endereco.getIdEndereco(), enderecoUpdateDTO));
    }

    @Test
    public void deveriaRemoverEnderecoComSucesso() throws Exception {
        Endereco endereco = retornarEndereco();

        when(enderecoRepository.findById(endereco.getIdEndereco())).thenReturn(Optional.of(endereco));

        enderecoService.removerEndereco(endereco.getIdEndereco());

        verify(enderecoRepository, times(1)).deleteById(endereco.getIdEndereco());
    }

    @Test
    public void deveriaListarEnderecosPorUsuarioComSucesso() throws Exception {
        Endereco endereco = retornarEndereco();
        EnderecoDTO enderecoDTO = retornarEnderecoDTO(endereco);
        List<Endereco> enderecosMock = List.of(endereco, endereco, endereco);
        List<EnderecoDTO> enderecosDTOMock = List.of(enderecoDTO, enderecoDTO, enderecoDTO);

        when(usuarioService.getIdLoggedUser()).thenReturn(endereco.getUsuario().getIdUsuario());
        when(enderecoRepository.findAllByUsuarioIdUsuario(endereco.getUsuario().getIdUsuario())).thenReturn(enderecosMock);

        List<EnderecoDTO> listaDTORetornada = enderecoService.listarEnderecosPorUsuario(endereco.getUsuario().getIdUsuario());

        assertNotNull(listaDTORetornada);
        assertEquals(enderecosDTOMock.size(), listaDTORetornada.size());
        assertIterableEquals(enderecosDTOMock, listaDTORetornada);
    }

    @Test
    public void naoDeveriaListarEnderecosPorUsuarioDeOutroUsuario() {
        Endereco endereco = retornarEndereco();

        when(usuarioService.getIdLoggedUser()).thenReturn(1);

        assertThrows(RegraDeNegocioException.class, () -> enderecoService.listarEnderecosPorUsuario(endereco.getUsuario().getIdUsuario()));
    }

    @Test
    public void deveriaGerarMensagemDeEmail() {
        Endereco enderecoMock = retornarEndereco();

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

    public static EnderecoDTO retornarEnderecoDTO(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdEndereco(endereco.getIdEndereco());
        enderecoDTO.setIdUsuario(endereco.getUsuario().getIdUsuario());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setPais(endereco.getPais());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setRegiao(endereco.getRegiao());

        return enderecoDTO;
    }

    public static Endereco retornarEndereco(){
        Usuario usuario = retornarUsuario();

        Endereco endereco = new Endereco();
        endereco.setIdEndereco(new Random().nextInt());
        endereco.setUsuario(usuario);
        endereco.setLogradouro("Avenida da Capoeira");
        endereco.setNumero("112");
        endereco.setComplemento("Apartamento 1");
        endereco.setCidade("Campinas");
        endereco.setEstado("São Paulo");
        endereco.setPais("Brasil");
        endereco.setRegiao("SP - Interior");
        endereco.setCep("19654-321");

        return endereco;
    }

    public static Usuario retornarUsuario(){
        Integer idUsuario = new Random().nextInt();
        Set<Cargo> cargos = Set.of(new Cargo());
        TipoAtivo tipoAtivo = TipoAtivo.fromString("S");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome("Anderson");
        usuario.setSobrenome("Luiz");
        usuario.setCpf("09876543211");
        usuario.setDataNascimento(LocalDate.parse("2010-10-10"));
        usuario.setEmail("anderson.turkiewicz@dbccompany.com");
        usuario.setLogin("aluiz");
        usuario.setSenha("12345678");
        usuario.setCargos(cargos);
        usuario.setTipoAtivo(tipoAtivo);
        usuario.setCarteira(new Carteira(usuario));

        return usuario;
    }

    private static EnderecoCreateDTO retornarEnderecoCreateDTO() {
        EnderecoCreateDTO endereco = new EnderecoCreateDTO();
        endereco.setIdUsuario(new Random().nextInt());
        endereco.setLogradouro("Avenida da Capoeira");
        endereco.setNumero("112");
        endereco.setComplemento("Apartamento 1");
        endereco.setCidade("Campinas");
        endereco.setEstado("São Paulo");
        endereco.setPais("Brasil");
        endereco.setRegiao("SP - Interior");
        endereco.setCep("19654-321");

        return endereco;
    }

    private static EnderecoUpdateDTO retornarEnderecoUpdateDTO(Endereco endereco) {
        EnderecoUpdateDTO enderecoDTO = new EnderecoUpdateDTO();
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setPais(endereco.getPais());
        enderecoDTO.setCep(endereco.getCep());

        return enderecoDTO;
    }
}
