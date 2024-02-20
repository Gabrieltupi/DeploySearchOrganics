package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoResponsePedidoDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.repository.PedidoRepository;
import com.vemser.dbc.searchorganic.repository.PedidoXProdutoRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import com.vemser.dbc.searchorganic.service.mocks.*;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Pedido Service - Teste")
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private PedidoXProdutoRepository pedidoXProdutoRepository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private EnderecoService enderecoService;
    @Mock
    private CupomService cupomService;
    @Mock
    private UsuarioService usuarioService;
    @Mock
    private EmailService emailService;
    @Mock
    private IValidarPedido validarPedido;
    @Mock
    private ProdutoService produtoService;
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private EmpresaService empresaService;
    @Mock
    private BigDecimal TAXA_SERVICO = new BigDecimal(5);
    @Spy
    @InjectMocks
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        Usuario admin = MockUsuario.retornarUsuarioAdmin();
        admin.setCarteira(MockCarteira.retornarCarteira());
        admin.getCarteira().setSaldo(BigDecimal.valueOf(500000));
        lenient().when(usuarioService.findByLogin("admin")).thenReturn(Optional.of(admin));
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
        assertEquals(pedidoDTO.getEmpresaDTO().getIdEmpresa(), pedidoRetornado.getEmpresaDTO().getIdEmpresa());

        Assertions.assertAll(
                () -> assertEquals(pedidoRetornado.getIdPedido(), pedidoDTO.getIdPedido()),
                () -> assertEquals(pedidoRetornado.getUsuario().getIdUsuario(), pedidoDTO.getUsuario().getIdUsuario()),
                () -> assertEquals(pedidoRetornado.getProdutos().size(), pedidoDTO.getProdutos().size())
        );

    }

    @Test
    @DisplayName("sucessoCancelarPedido")
    public void sucessoCancelarPedido() throws Exception {
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Produto produto = MockProduto.retornarProdutoEntity();
        List<PedidoXProduto> pedidoXProduto = MockPedido.retornaListaProdutoXPedido(pedido, produto);
        ProdutoResponsePedidoDTO produtoRespDTO = new ProdutoResponsePedidoDTO(produto);
        when(pedidoRepository.findById(pedido.getIdPedido())).thenReturn(Optional.of(pedido));
        when(objectMapper.convertValue(pedidoXProduto.get(0).getProduto(), ProdutoResponsePedidoDTO.class)).thenReturn(produtoRespDTO);
        when(objectMapper.convertValue(any(PedidoDTO.class), eq(Pedido.class))).thenReturn(pedido);
        when(usuarioService.getIdLoggedUser()).thenReturn(pedido.getUsuario().getIdUsuario());
        when(pedidoXProdutoRepository.findAllByIdPedido(pedido.getIdPedido())).thenReturn(pedidoXProduto);

        pedidoService.cancelarPedido(pedido.getIdPedido());

        verify(pedidoRepository, times(1)).cancelarPedido(pedido.getIdPedido());

    }


    @Test
    @DisplayName("efetuarTransferenciaSaldoSuficiente")
    public void efetuarTransferenciaSaldoSuficiente() throws RegraDeNegocioException {
        // Cenário
        Carteira carteiraOrigem = MockCarteira.retornarCarteira();
        carteiraOrigem.setSaldo(carteiraOrigem.getSaldo());
        Carteira carteiraDestino = MockCarteira.retornarCarteira2();
        BigDecimal total = new BigDecimal("50");

        // Execução do método a ser testado
        pedidoService.efetuarTransferencia(carteiraOrigem, carteiraDestino, total);

        // Verificação
        assertEquals(new BigDecimal("50"), carteiraOrigem.getSaldo());
        assertEquals(new BigDecimal("150"), carteiraDestino.getSaldo());
    }

    @Test
    @DisplayName("efetuarTransferenciaSaldoInsuficiente")
    public void efetuarTransferenciaSaldoInsuficiente() {
        // Cenário
        Carteira carteiraOrigem = MockCarteira.retornarCarteira();
        carteiraOrigem.setSaldo(new BigDecimal("40")); // Definindo um saldo menor do que o total a ser transferido
        Carteira carteiraDestino = MockCarteira.retornarCarteira2();
        BigDecimal total = new BigDecimal("50");

        // Execução e Verificação
        assertThrows(RegraDeNegocioException.class, () -> {
            pedidoService.efetuarTransferencia(carteiraOrigem, carteiraDestino, total);
        });

        // Verificação adicional para garantir que o saldo das carteiras permanece inalterado
        assertEquals(new BigDecimal("40"), carteiraOrigem.getSaldo());
        assertEquals(new BigDecimal("100"), carteiraDestino.getSaldo());
    }

    @Test
    @DisplayName("sucessoAcharTodosPorIdUsuario")
    void sucessoAcharTodosPorIdUsuario() throws Exception {
        Usuario usuario = MockUsuario.retornarUsuario();
        Integer idUsuario = usuario.getIdUsuario();
        Pedido pedidoEntity = MockPedido.retornaPedidoEntity();
        pedidoEntity.setUsuario(usuario);
        List<PedidoXProduto> pedidoXProduto = MockPedido.retornaListaProdutoXPedido(pedidoEntity, MockProduto.retornarProdutoEntity());
        PedidoDTO pedidoDTO = MockPedido.retornaPedidoDTOPorEntity(pedidoEntity, pedidoXProduto);

        when(usuarioService.getIdLoggedUser()).thenReturn(idUsuario);
        when(pedidoRepository.findAllByUsuario_IdUsuario(idUsuario)).thenReturn(Collections.singletonList(pedidoEntity));
        when(pedidoXProdutoRepository.findAllByIdPedido(pedidoEntity.getIdPedido())).thenReturn(pedidoXProduto);

        List<PedidoDTO> pedidosRetornados = pedidoService.findAllByIdUsuario(idUsuario);

        verify(usuarioService).getIdLoggedUser();
        verify(pedidoRepository).findAllByUsuario_IdUsuario(idUsuario);
        assertEquals(1, pedidosRetornados.size());

        PedidoDTO primeiroPedidoRetornado = pedidosRetornados.get(0);
        assertEquals(pedidoDTO.getIdPedido(), primeiroPedidoRetornado.getIdPedido());
        assertEquals(pedidoDTO.getEmpresaDTO().getIdEmpresa(), primeiroPedidoRetornado.getEmpresaDTO().getIdEmpresa());
        assertEquals(pedidoDTO.getUsuario().getIdUsuario(), primeiroPedidoRetornado.getUsuario().getIdUsuario());
        assertEquals(pedidoDTO.getProdutos().size(), primeiroPedidoRetornado.getProdutos().size());
    }

    @Test
    @DisplayName("obterProdutos - Sucesso")
    public void obterProdutosSucesso() throws Exception {
        Produto produto = MockProduto.retornarProdutoEntity();
        ArrayList<ProdutoCarrinhoCreate> produtosCarrinhoCreate = new ArrayList<>();
        ProdutoCarrinhoCreate produtoCarrinhoCreate = new ProdutoCarrinhoCreate();
        produtoCarrinhoCreate.setIdProduto(produto.getIdProduto());
        produtoCarrinhoCreate.setQuantidade(2);
        produtosCarrinhoCreate.add(produtoCarrinhoCreate);

        List<Produto> produtosBanco = new ArrayList<>();
        ProdutoDTO produtoDTO = MockProduto.retornarProdutoDTO();

        when(produtoService.findById(produtoCarrinhoCreate.getIdProduto())).thenReturn(produtoDTO);

        List<PedidoXProduto> produtosRetornados = pedidoService.obterProdutos(produtosCarrinhoCreate, produtosBanco);

        assertEquals(1, produtosRetornados.size());
        PedidoXProduto primeiroProdutoRetornado = produtosRetornados.get(0);

        assertEquals(2, primeiroProdutoRetornado.getQuantidade());

    }

    @Test
    @DisplayName("sucessoRetornarDto")
    public void sucessoRetornarDto() {
        Usuario usuario = MockUsuario.retornarUsuario();
        Empresa empresa = MockEmpresa.retornarEmpresa();
        Cupom cupom = MockCupom.retornarCupom();
        Endereco endereco = MockEndereco.retornarEndereco();

        List<PedidoXProduto> pedidoProdutos = MockPedido.retornaListaPedidoXPROduto();
        when(pedidoXProdutoRepository.findAllByIdPedido(any(Integer.class))).thenReturn(pedidoProdutos);

        ProdutoResponsePedidoDTO produtoResponsePedidoDTO = MockProduto.retornarProdutoResponsePedidoDTO();
        when(objectMapper.convertValue(any(), eq(ProdutoResponsePedidoDTO.class))).thenReturn(produtoResponsePedidoDTO);

        PedidoDTO pedidoDTO = pedidoService.retornarDto(MockPedido.retornaPedidoEntity());

        assertNotNull(pedidoDTO);
        assertEquals(usuario.getIdUsuario(), pedidoDTO.getUsuario().getIdUsuario());
        assertEquals(empresa.getIdEmpresa(), pedidoDTO.getEmpresaDTO().getIdEmpresa());
        assertEquals(cupom.getIdCupom(), pedidoDTO.getCupom().getIdCupom());
        assertEquals(endereco.getIdEndereco(), pedidoDTO.getEndereco().getIdEndereco());
        assertEquals(pedidoProdutos.size(), pedidoDTO.getProdutos().size());
    }

    @Test
    @DisplayName("sucessoAcharTodos")
    public void sucessoAcharTodos() throws Exception {
        Usuario admin = MockUsuario.retornarUsuarioAdmin();
        when(usuarioService.getIdLoggedUser()).thenReturn(admin.getIdUsuario());

        List<Pedido> pedidosMock = MockPedido.retornaListaPedidoEntity();
        when(pedidoRepository.findAll()).thenReturn(pedidosMock);

        List<PedidoDTO> pedidoDTOList = pedidoService.findAll();

        assertNotNull(pedidoDTOList);

        assertEquals(pedidosMock.size(), pedidoDTOList.size());

    }

    @Test
    @DisplayName("atualizarStatus")
    public void atualizarStatus() throws Exception {
        Pedido pedido = MockPedido.retornaPedidoEntity(); // Pedido mockado
        StatusPedido novoStatus = StatusPedido.ENTREGUE; // Novo status do pedido
        Integer idEmpresa = pedido.getEmpresa().getIdEmpresa(); // ID da empresa do pedido

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        PedidoEmpresaDTO pedidoAtualizado = pedidoService.updateStatus(pedido, novoStatus, idEmpresa);

        assertNotNull(pedidoAtualizado);
        assertEquals(pedido.getIdPedido(), pedidoAtualizado.getIdPedido());
        assertEquals(novoStatus, pedidoAtualizado.getStatusPedido());
        assertEquals(idEmpresa, pedidoAtualizado.getIdEmpresa());
    }

    @Test
    @DisplayName("emailPedidoCriadoSucesso")
    public void emailPedidoCriadoSucesso() throws Exception {

        PedidoDTO pedidoDTO = MockPedido.retornaPedidoDto();
        Usuario usuario = MockUsuario.retornarUsuario();

        when(enderecoService.getMensagemEnderecoEmail(objectMapper.convertValue(pedidoDTO.getEndereco(), Endereco.class))).thenReturn("Endereço do pedido");
        when(produtoService.getMensagemProdutoEmail(pedidoDTO.getProdutos())).thenReturn("Detalhes dos produtos do pedido");

        pedidoService.emailPedidoCriado(pedidoDTO, usuario);

        String mensagemEsperada = """
                <span style="display: block; font-size: 16px; font-weight: bold;">Pedido Realizado</span>
                <section style="color: #ffffff;">
                    <p style="color: #ffffff;">Olá Usuario Teste, seu pedido foi realizado com sucesso,</p>
                    <p style="color: #ffffff;">O Status atual do seu pedido é PENDENTE,</p>
                    <p style="color: #ffffff;">Abaixo seguem detalhes do seu pedido:</p>
                    <p style="color: #ffffff;">Pedido N°: 1,</p>
                    <p style="color: #ffffff;">Total: 100.0,</p>
                    <p style="color: #ffffff;">Forma de pagamento: CARTAO_CREDITO,</p>
                    <p style="color: #ffffff;">Data do pedido: 01/01/2024,</p>
                    <p style="color: #ffffff;">Data da entrega: 10/01/2024,</p>
                    <p style="color: #ffffff;">Produtos: <br> Detalhes dos produtos do pedido </p>
                    <p style="color: #ffffff;">Endereço: <br> Endereço do pedido </p>
                </section>
                """;
//        verify(emailService).sendEmail(mensagemEsperada, "Pedido realizado", "usuario@teste.com");
    }

    @Test
    @DisplayName("sucessoSalvarPedido")
    public void sucessoSalvarPedido() throws Exception {
        PedidoCreateDTO pedidoCreateDTO = MockPedido.retornarPedidoCreateDto();
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Usuario usuario = MockUsuario.retornarUsuario();
        Produto produto = MockProduto.retornarProdutoEntity();
        ProdutoDTO produtoDTO = MockProduto.retornarProdutoDTO();

        when(objectMapper.convertValue(pedidoCreateDTO, Pedido.class)).thenReturn(pedido);
        when(produtoService.findById(anyInt())).thenReturn(produtoDTO);
        when(objectMapper.convertValue(produtoDTO,Produto.class)).thenReturn(produto);
        when(pedidoRepository.save(pedido)).thenReturn(pedido);
        when(usuarioService.obterUsuarioPorId(usuario.getIdUsuario())).thenReturn(usuario);
        when(enderecoService.getById(pedidoCreateDTO.getIdEndereco())).thenReturn(pedido.getEndereco());
        when( empresaService.getById(pedidoCreateDTO.getIdEmpresa())).thenReturn(pedido.getEmpresa());
        when(cupomService.getById(pedidoCreateDTO.getIdCupom())).thenReturn(pedido.getCupom());
        when(pedidoXProdutoRepository.findAllByIdPedido(pedido.getIdPedido())).thenReturn(MockPedido.retornaListaProdutoXPedido(pedido, produto));


        PedidoDTO pedidoDTO = pedidoService.save(usuario.getIdUsuario(), pedidoCreateDTO);

        assertNotNull(pedidoDTO);
        assertEquals(usuario.getIdUsuario(), pedidoDTO.getUsuario().getIdUsuario());


    }


    @Test
    @DisplayName("atualizarStatusDoPedido")
    public void atualizarStatusDoPedido() throws Exception {
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Integer idPedido = pedido.getIdPedido();
        pedido.setStatusPedido(StatusPedido.ENTREGUE);
        Integer idEmpresa = pedido.getEmpresa().getIdEmpresa();
        pedido.getEmpresa().setIdUsuario(3);

        when(usuarioService.getIdLoggedUser()).thenReturn(pedido.getEmpresa().getIdUsuario());
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        PedidoEmpresaDTO pedidoAtualizado = pedidoService.updatePedidoStatus(idPedido, pedido.getStatusPedido(), idEmpresa);

        assertNotNull(pedidoAtualizado);
        assertEquals(idPedido, pedidoAtualizado.getIdPedido());
        assertEquals(pedido.getStatusPedido(), pedidoAtualizado.getStatusPedido());
    }
    @Test
    @DisplayName("atualizarCodigoRastreio")
    public void atualizarCodigoRastreio() throws Exception {
        String codRastreio = "ABCDFEK";
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Usuario usuEmpresa = MockUsuario.retornarUsuario();
        pedido.getEmpresa().setIdUsuario(usuEmpresa.getIdUsuario());
        PedidoRastreioDTO pedRastreioDTO = MockPedido.retornarPedidoRastreioOk(pedido, codRastreio);

        when(usuarioService.getIdLoggedUser()).thenReturn(pedido.getEmpresa().getIdUsuario());
        when(pedidoRepository.findById(pedido.getIdPedido())).thenReturn(Optional.of(pedido));
        when(objectMapper.convertValue(pedido, PedidoRastreioDTO.class)).thenReturn(pedRastreioDTO);
        PedidoRastreioDTO pedidoRastreioDTO = pedidoService.updateCodigoDeRastreio(pedido.getIdPedido(), codRastreio);

        assertNotNull(pedidoRastreioDTO);
        assertEquals(pedido.getCodigoDeRastreio(), "ABCDFEK");
        assertEquals(pedido.getStatusPedido(), pedidoRastreioDTO.getStatusPedido());
    }


    @Test
    @DisplayName("entregueSucesso")
    public void entregueSucesso() throws Exception {
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Integer idPedido = pedido.getIdPedido();
        pedido.setStatusPedido(StatusPedido.A_CAMINHO);
        Usuario usuarioEmpresa = MockUsuario.retornarUsuario();
        usuarioEmpresa.setCarteira(MockCarteira.retornarCarteira());
        pedido.getEmpresa().setIdUsuario(usuarioEmpresa.getIdUsuario());

        when(usuarioService.obterUsuarioPorId(pedido.getEmpresa().getIdEmpresa())).thenReturn(usuarioEmpresa);
        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(usuarioService.getIdLoggedUser()).thenReturn(pedido.getUsuario().getIdUsuario());

        PedidoDTO pedidoDTO = pedidoService.entregue(idPedido);

        assertNotNull(pedidoDTO);
        assertEquals(idPedido, pedidoDTO.getIdPedido());
        assertEquals(StatusPedido.ENTREGUE, pedido.getStatusPedido());
    }


    @Test
    @DisplayName("pagamentoComCartao")
    public void pagamentoComCartao() throws Exception {
        // Cenário
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Integer idPedido = pedido.getIdPedido();
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

        PagamentoDTO pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setFormaPagamento(FormaPagamento.CREDITO);
        pagamentoDTO.setNumeroCartao("1234567890123456");
        pagamentoDTO.setCvv("123");
        pagamentoDTO.setDataValidade(LocalDate.parse("2025-12-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(usuarioService.getIdLoggedUser()).thenReturn(pedido.getUsuario().getIdUsuario());

        PedidoDTO pedidoDTO = pedidoService.pagamento(idPedido, pagamentoDTO);

        assertNotNull(pedidoDTO);
        assertEquals(idPedido, pedidoDTO.getIdPedido());
        assertEquals(StatusPedido.PAGO, pedido.getStatusPedido());
        assertEquals(FormaPagamento.CREDITO, pedido.getFormaPagamento());
    }

    @Test
    @DisplayName("pagamentoComCartaoInvalido")
    public void pagamentoComCartaoInvalido() throws Exception {
        //
        Pedido pedido = MockPedido.retornaPedidoEntity();
        Integer idPedido = pedido.getIdPedido();
        pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);

        PagamentoDTO pagamentoDTO = new PagamentoDTO();
        pagamentoDTO.setFormaPagamento(FormaPagamento.CREDITO);

        pagamentoDTO.setNumeroCartao(null);
        pagamentoDTO.setCvv(null);
        pagamentoDTO.setDataValidade(null);

        when(pedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));
        when(usuarioService.getIdLoggedUser()).thenReturn(pedido.getUsuario().getIdUsuario());

        assertThrows(RegraDeNegocioException.class, () -> pedidoService.pagamento(idPedido, pagamentoDTO), "Cartão inválido");
    }

    @Test
    @DisplayName("sucessoAtualizacaoPedido")
    public void sucessoAtualizacaoPedido() throws Exception {

        Pedido pedido = MockPedido.retornaPedidoEntity();
        Integer id = pedido.getIdPedido();
        PedidoUpdateDTO pedidoAtualizar = MockPedido.retornarPedidoUpdateDto();


        when(usuarioService.getIdLoggedUser()).thenReturn(pedido.getUsuario().getIdUsuario());
        when(pedidoRepository.findById(id)).thenReturn(java.util.Optional.of(pedido));
        when(enderecoService.getById(pedidoAtualizar.getIdEndereco())).thenReturn(pedido.getEndereco());

        PedidoDTO pedidoAtualizado = pedidoService.update(id, pedidoAtualizar);

        assertNotNull(pedidoAtualizado);
        assertEquals(pedido.getIdPedido(), pedidoAtualizado.getIdPedido());
        assertEquals(pedido.getEndereco().getEstado(), pedidoAtualizado.getEndereco().getEstado());
        assertEquals(pedidoAtualizar.getFormaPagamento(), pedidoAtualizado.getFormaPagamento());
        assertEquals(pedidoAtualizar.getDataEntrega(), pedidoAtualizado.getDataEntrega());
        assertEquals(pedidoAtualizar.getStatusPedido(), pedidoAtualizado.getStatusPedido());
        assertEquals(pedidoAtualizar.getPrecoFrete(), pedidoAtualizado.getPrecoFrete());

        verify(pedidoRepository).save(pedido);
    }


}