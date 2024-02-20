package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioCreateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.CargoRepository;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import com.vemser.dbc.searchorganic.service.mocks.MockUsuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UsuarioService - Test")
class UsuarioServiceTest {
    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private ObjectMapper objectMapper;

    private final Usuario usuarioMock = MockUsuario.retornarUsuario();
    private final UsuarioDTO usuarioDTOMock = MockUsuario.retornarUsuarioDTO(usuarioMock);
    private final List<Usuario> usuariosMock = MockUsuario.retornarUsuarios(usuarioMock);
    private final UsuarioCreateDTO usuarioCreateDTOMock = MockUsuario.retornarUsuarioCreateDTO(usuarioMock);
    private final Cargo cargoMock = MockUsuario.obterCargo("ROLE_USUARIO");

    @Test
    @DisplayName("Deveria retornar um usuario por id com sucesso")
    public void deveriaRetornarUsuarioPorId() throws Exception {
        when(usuarioRepository.getById(usuarioMock.getIdUsuario())).thenReturn(usuarioMock);

        doReturn(1).when(usuarioRepository).existsAdminCargoByUserId(0);

        Usuario usuarioRetornado = usuarioService.obterUsuarioPorId(usuarioMock.getIdUsuario());

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioMock, usuarioRetornado);
    }

    @Test
    @DisplayName("Não deveria retornar o usuario para outro usuário")
    public void naoDeveriaRetornarUsuarioParaOutroUsuario() {
        doReturn(0).when(usuarioRepository).existsAdminCargoByUserId(0);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> usuarioService.obterUsuarioPorId(usuarioMock.getIdUsuario()));
        assertEquals("Só é possivel retornar seus próprios dados.", exception.getMessage());
    }

    @Test
    @DisplayName("Deveria listar usuários com sucesso")
    public void deveriaListarUsuariosComSucesso() throws Exception {
        when(usuarioRepository.findAll()).thenReturn(usuariosMock);
        doReturn(1).when(usuarioRepository).existsAdminCargoByUserId(0);

        List<Usuario> usuarioRetornado = usuarioService.exibirTodos();

        assertNotNull(usuarioRetornado);
    }

    @Test
    @DisplayName("Não deveria listar usuários para usuário não admin")
    public void naoDeveriaListarUsuariosParaUsuarioNaoAdmin() {
        doReturn(0).when(usuarioRepository).existsAdminCargoByUserId(0);

        RegraDeNegocioException exception = assertThrows(RegraDeNegocioException.class, () -> usuarioService.exibirTodos());
        assertEquals("Apenas o administrador pode ver a lista inteira do banco de dados de usuário.", exception.getMessage());
    }

    @Test
    @DisplayName("Deveria criar um usuario com sucesso")
    public void deveriaCriarUsuarioComSucesso() throws Exception {
        when(usuarioRepository.save(any())).thenReturn(usuarioMock);

        Usuario usuarioCriado = usuarioService.criarUsuario(usuarioMock);

        assertNotNull(usuarioCriado);
        assertEquals(usuarioMock, usuarioCriado);
    }

    @Test
    @DisplayName("Deveria criar um usuario dto com sucesso")
    public void deveriaCriarUsuarioDTOComSucesso() throws Exception {
        when(usuarioRepository.save(any())).thenReturn(usuarioMock);
        when(objectMapper.convertValue(usuarioCreateDTOMock, Usuario.class)).thenReturn(usuarioMock);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(cargoRepository.findByNome("ROLE_USUARIO")).thenReturn(cargoMock);

        UsuarioDTO usuarioCriado = usuarioService.criarUsuario(usuarioCreateDTOMock);


        assertNotNull(usuarioCriado);
        assertEquals(usuarioDTOMock.getIdUsuario(), usuarioCriado.getIdUsuario());
    }

    @Test
    @DisplayName("Deveria atualizar um usuário com sucesso")
    public void deveriaAtualizarUsuario() throws Exception {
        Usuario usuarioAntigo = new Usuario();
        BeanUtils.copyProperties(usuarioMock, usuarioAntigo);
        usuarioMock.setNome("Novo Nome");

        when(usuarioRepository.getById(usuarioMock.getIdUsuario())).thenReturn(usuarioMock);
        when(usuarioRepository.save(usuarioMock)).thenReturn(usuarioAntigo);
        doReturn(1).when(usuarioRepository).existsAdminCargoByUserId(0);

        Usuario usuarioAtualizado =  usuarioService.editarUsuario(usuarioMock.getIdUsuario(), usuarioMock);

        assertNotNull(usuarioAtualizado);
        assertNotEquals(usuarioAntigo, usuarioAtualizado);
        assertNotEquals(usuarioAntigo.getNome(), usuarioAtualizado.getNome());
    }

    @Test
    @DisplayName("Deveria remover um usuário com sucesso")
    public void deveriaRemoverUsuarioComSucesso() throws Exception {
        doReturn(1).when(usuarioRepository).existsAdminCargoByUserId(0);
        when(usuarioRepository.getById(usuarioMock.getIdUsuario())).thenReturn(usuarioMock);

        usuarioService.removerUsuario(usuarioMock.getIdUsuario());

        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Não deveria remover um usuário")
    public void deveriaRemoverUsuarioComSucessos() {
        assertThrows(RegraDeNegocioException.class, () -> usuarioService.removerUsuario(usuarioMock.getIdUsuario()));
    }

    @Test
    @DisplayName("Deveria buscar um usuário por login com sucesso")
    public void deveriaBuscarUsuarioPorLogin() {
        when(usuarioRepository.findByLogin(any())).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> usuarioRetornado = usuarioService.findByLogin(usuarioMock.getLogin());

        assertNotNull(usuarioRetornado);
        assertEquals(Optional.of(usuarioMock), usuarioRetornado);
    }

    @Test
    @DisplayName("Deveria buscar um usuário por login e email com sucesso")
    public void deveriaBuscarUsuarioPorLoginEmail() throws Exception {
        when(usuarioRepository.findByLoginAndEmail(any(), any())).thenReturn(Optional.of(usuarioMock));

        Usuario usuarioRetornado = usuarioService.findByLoginAndEmail(usuarioMock.getLogin(), usuarioMock.getEmail());

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioMock, usuarioRetornado);
    }

    @Test
    @DisplayName("Não deveria retornar um usuário com login e email inválidos")
    public void naoDeveriaRetornarUsuarioComLoginEmailInvalido() throws Exception {
        when(usuarioRepository.findByLoginAndEmail(any(), any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> usuarioService.findByLoginAndEmail(any(), any()));
        assertEquals("Usuário não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deveria buscar um usuário por email com sucesso")
    public void deveriaBuscarUsuarioPorEmail() throws Exception {
        when(usuarioRepository.findByEmail(any())).thenReturn(Optional.of(usuarioMock));

        UsuarioDTO usuarioRetornado = usuarioService.findByEmail(usuarioMock.getEmail());

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioDTOMock.getEmail(), usuarioRetornado.getEmail());
    }

    @Test
    @DisplayName("Deveria buscar um usuário por cpf com sucesso")
    public void deveriaBuscarUsuarioPorCpf() throws Exception {
        when(usuarioRepository.findByCpf(any())).thenReturn(Optional.of(usuarioMock));

        UsuarioDTO usuarioRetornado = usuarioService.findByCpf(usuarioMock.getCpf());

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioDTOMock.getCpf(), usuarioRetornado.getCpf());
    }

    @Test
    @DisplayName("Deveria buscar um usuário por login e senha com sucesso")
    public void deveriaBuscarUsuarioPorLoginSenha() {
        when(usuarioRepository.findByLoginAndSenha(any(), any())).thenReturn(Optional.of(usuarioMock));

        Optional<Usuario> usuarioRetornado = usuarioService.findByLoginAndSenha(usuarioMock.getLogin(), usuarioMock.getSenha());

        assertNotNull(usuarioRetornado);
        assertEquals(Optional.of(usuarioMock), usuarioRetornado);
    }

    @Test
    @DisplayName("Deveria salvar um usuario com sucesso")
    public void deveriaSalvarUsuarioComSucesso() {
        when(usuarioRepository.save(any())).thenReturn(usuarioMock);

        Usuario usuarioCriado = usuarioService.salvarUsuario(usuarioMock);

        assertNotNull(usuarioCriado);
        assertEquals(usuarioMock, usuarioCriado);
    }

    @Test
    @DisplayName("Deveria buscar o usuario logado com sucesso")
    public void deveriaBuscarUsuarioLogadoComSucesso() throws Exception {
        when(usuarioRepository.findById(any())).thenReturn(Optional.of(usuarioMock));
        when(objectMapper.convertValue(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(usuarioDTOMock);

        UsuarioDTO usuarioRetornado = usuarioService.obterUsuarioLogado();

        assertNotNull(usuarioRetornado);
        assertEquals(usuarioDTOMock, usuarioRetornado);
    }

    @Test
    @DisplayName("Não deveria retornar o usuario logado inexistente")
    public void NaoDeveriaRetornarUsuarioLogadoInexistente() {
        when(usuarioRepository.findById(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> usuarioService.obterUsuarioLogado());
        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}