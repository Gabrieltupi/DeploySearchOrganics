package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.repository.PedidoRepository;
import com.vemser.dbc.searchorganic.repository.PedidoXProdutoRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import com.vemser.dbc.searchorganic.service.mocks.*;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pedido Service - Teste")
class PedidoServiceTest {

    @Mock
    private  PedidoRepository pedidoRepository;
    @Mock
    private  ProdutoRepository produtoRepository;
    @Mock
    private  PedidoXProdutoRepository pedidoXProdutoRepository;
    @Mock
    private  ObjectMapper objectMapper;
    @Mock
    private  EnderecoService enderecoService;
    @Mock
    private  CupomService cupomService;
    @Mock
    private  UsuarioService usuarioService;
    @Mock
    private  EmailService emailService;
    @Mock
    private  ProdutoService produtoService;
    @Mock
    private  List<IValidarPedido> validarPedidoList;
    @Mock
    private  UsuarioRepository usuarioRepository;
    @Mock
    private  EmpresaService empresaService;
    @Mock
    private  BigDecimal TAXA_SERVICO = new BigDecimal(5);
    @Spy
    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp(){
        Usuario admin = MockUsuario.retornarUsuarioAdmin();
        when(usuarioService.findByLogin("admin")).thenReturn(Optional.ofNullable(admin));
    }
    @Test
    @DisplayName("sucessoAcharPorId")
    void sucessoAcharPorId() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        Pedido pedidoEntity = MockPedido.retornaPedidoEntity();
        pedidoEntity.setUsuario(usuario);
        List<PedidoXProduto> pedidoXProduto = MockPedido.retornaListaProdutoXPedido(pedidoEntity, MockProduto.retornarProdutoEntity());

        PedidoDTO pedidoDTO = MockPedido.retornaPedidoDTOPorEntity(pedidoEntity, pedidoXProduto);

        when(usuarioService.getIdLoggedUser()).thenReturn(usuario.getIdUsuario());
        when(pedidoRepository.findById(pedidoEntity.getIdPedido())).thenReturn(Optional.of(pedidoEntity));
        when(pedidoXProdutoRepository.findAllByIdPedido(pedidoEntity.getIdPedido())).thenReturn(pedidoXProduto);
        PedidoDTO pedidoRetornado = pedidoService.getById(pedidoEntity.getIdPedido());

        verify(usuarioService).getIdLoggedUser();
        verify(pedidoRepository).findById(pedidoRetornado.getIdPedido());
        assertEquals(pedidoDTO, pedidoRetornado);
        Assertions.assertAll(
                () -> assertEquals(pedidoRetornado.getIdPedido(), pedidoDTO.getIdPedido()),
                () -> assertEquals(pedidoRetornado.getUsuario().getIdUsuario(), pedidoDTO.getUsuario().getIdUsuario()),
                () -> assertEquals(pedidoRetornado.getProdutos().size(), pedidoDTO.getProdutos().size())
        );

    }

    @Test
    @DisplayName("sucessoAcharTodosPedidos")
    void sucessoAcharTodosPedidos() throws Exception {
        List<Pedido> pedidos = MockPedido.retornaListaPedidoEntity();
//        List<PedidoDTO> pedidoDto=MockPedido.retornaListaPedidoDTO();
        PedidoDTO pedidoDto= MockPedido.retornaPedidoDto();
        when(pedidoRepository.findAll()).thenReturn(pedidos);
        when(pedidoService.retornarDto(any())).thenReturn(pedidoDto);

        List<PedidoDTO> listaPedidosDto = pedidoService.findAll();

        assertNotNull(listaPedidosDto);
        assertEquals(pedidos.size(), listaPedidosDto.size());

        verify(pedidoRepository).findAll();
        verify(pedidoService, times(pedidos.size())).retornarDto(any());
    }


    @Test
    @DisplayName("sucessoSalvarPedido")
    void sucessoSalvarPedido() throws Exception {
        ArgumentCaptor<Pedido> pedidoArgumentCaptor = ArgumentCaptor.forClass(Pedido.class);

        Integer loggedUserId = 1;
//        Integer idPedido = 1;
        PedidoCreateDTO pedidoCreateDTO = MockPedido.retornarPedidoCreateDto();
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Produto produto = MockProduto.retornarProdutoEntity();
        List<Produto> produtosBanco = MockProduto.retornarListaProdutos();
        List<PedidoXProduto> produtos = MockPedido.retornaListaProdutoXPedido(pedido,produto);
        Usuario usuario = MockUsuario.retornarUsuario();
        usuario.setIdUsuario(loggedUserId);
        Endereco endereco = MockEndereco.retornarEndereco();
        Empresa empresa = MockEmpresa.retornarEmpresa();
        Cupom cupom = new Cupom(1, "cupom10", TipoAtivo.S, "cupom 10 de desconto", BigDecimal.valueOf(10),
                MockEmpresa.retornarEmpresa().getIdEmpresa());
        PedidoDTO pedidoDTO = MockPedido.retornaPedidoDto();

        when(usuarioService.getIdLoggedUser()).thenReturn(usuario.getIdUsuario());
        when(usuarioService.obterUsuarioPorId(usuario.getIdUsuario())).thenReturn(usuario);
        when(enderecoService.getById(pedidoCreateDTO.getIdEndereco())).thenReturn(endereco);
        when(empresaService.getById(pedidoCreateDTO.getIdEmpresa())).thenReturn(empresa);
        when(cupomService.getById(pedidoCreateDTO.getIdCupom())).thenReturn(cupom);
        when(objectMapper.convertValue(eq(pedidoCreateDTO), eq(Pedido.class))).thenReturn(pedido);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(pedidoXProdutoRepository.save(any(PedidoXProduto.class))).thenReturn(null);
//        doNothing().when(emailService).emailPedidoCriado(any(PedidoDTO.class), any(Usuario.class));

        PedidoDTO resultado = pedidoService.save(usuario.getIdUsuario(), pedidoCreateDTO);

        verify(usuarioService).getIdLoggedUser();
        verify(usuarioService).obterUsuarioPorId(usuario.getIdUsuario());
        verify(enderecoService).getById(pedidoCreateDTO.getIdEndereco());
        verify(empresaService).getById(pedidoCreateDTO.getIdEmpresa());
        verify(cupomService).getById(pedidoCreateDTO.getIdCupom());
        verify(objectMapper).convertValue(eq(pedidoCreateDTO), eq(Pedido.class));
        verify(pedidoRepository).save(pedidoArgumentCaptor.capture());
        verify(pedidoXProdutoRepository, times(produtos.size())).save(any(PedidoXProduto.class));
//        verify(emailService).emailPedidoCriado(any(PedidoDTO.class), any(Usuario.class));

        Pedido pedidoSalvo = pedidoArgumentCaptor.getValue();
        assertEquals(pedidoDTO, resultado);

    }











}