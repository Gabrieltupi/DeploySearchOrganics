package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoResponsePedidoDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.model.pk.ProdutoXPedidoPK;
import com.vemser.dbc.searchorganic.repository.PedidoRepository;
import com.vemser.dbc.searchorganic.repository.PedidoXProdutoRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final PedidoXProdutoRepository pedidoXProdutoRepository;
    private final ObjectMapper objectMapper;
    private final EnderecoService enderecoService;
    private final CupomService cupomService;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final ProdutoService produtoService;
    private final List<IValidarPedido> validarPedidoList;

    public Pedido findById(Integer id) throws Exception {
        return pedidoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado: " + id));
    }

    public List<PedidoDTO> findAll() throws Exception {
        return pedidoRepository.findAll().stream()
                .map(this::retornarDto)
                .collect(Collectors.toList());
    }

    public PedidoDTO save(Integer id, PedidoCreateDTO pedidoCreateDTO) throws Exception {
        Pedido pedido = objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
        List<Produto> produtosBanco = new ArrayList<>();
        List<PedidoXProduto> produtos = obterProdutos(pedidoCreateDTO.getProdutosCarrinho(), produtosBanco);

        Usuario usuario = usuarioService.obterUsuarioPorId(id);
        Endereco endereco = enderecoService.getById(pedidoCreateDTO.getIdEndereco());

        pedido.setEndereco(endereco);
        pedido.setUsuario(usuario);

        if (pedidoCreateDTO.getIdCupom() != null) {
            Cupom cupom = cupomService.getById(pedidoCreateDTO.getIdCupom());
            pedido.setCupom(cupom);
        }

        for (IValidarPedido validador : validarPedidoList) {
            validador.validar(pedido, id, produtos);
        }


        pedido = pedidoRepository.save(pedido);

        for (PedidoXProduto pedidoXProduto : produtos) {
            pedidoXProduto.setPedido(pedido);
            System.out.println(pedidoXProduto);
            Produto produto = pedidoXProduto.getProduto();
            pedidoXProduto.setProdutoXPedidoPK(new ProdutoXPedidoPK(produto.getIdProduto(), pedido.getIdPedido()));
            pedidoXProdutoRepository.save(pedidoXProduto);
            BigDecimal quantidadeAtualizada = produto.getQuantidade().subtract(BigDecimal.valueOf(pedidoXProduto.getQuantidade()));
            pedidoXProdutoRepository.updateQuantidadeProduto(produto.getIdProduto(), quantidadeAtualizada);
        }

        PedidoDTO pedidoDTO = this.retornarDto(pedido);

        this.emailPedidoCriado(pedidoDTO, usuario);

        return pedidoDTO;
    }

    public PedidoDTO update(Integer id, PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = findById(id);
        Endereco endereco = enderecoService.getById(pedidoAtualizar.getIdEndereco());

        pedidoEntity.setEndereco(endereco);

        pedidoEntity.setFormaPagamento(pedidoAtualizar.getFormaPagamento());
        pedidoEntity.setDataEntrega(pedidoAtualizar.getDataEntrega());
        pedidoEntity.setStatusPedido(pedidoAtualizar.getStatusPedido());
        pedidoEntity.setPrecoFrete(pedidoAtualizar.getPrecoFrete());

        pedidoRepository.save(pedidoEntity);

        return retornarDto(pedidoEntity);
    }

    public void cancelarPedido(Integer idPedido) throws Exception {
        Pedido pedido = findById(idPedido);
        List<PedidoXProduto> produtos = pedidoXProdutoRepository.findAllByIdPedido(pedido.getIdPedido());
        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();

            produto.setQuantidade(produto.getQuantidade().add(BigDecimal.valueOf(pedidoXProduto.getQuantidade())));

            produtoRepository.save(produto);
        }

        pedidoRepository.cancelarPedido(idPedido);
    }

    public List<PedidoDTO> findAllByIdUsuario(Integer idUsuario) throws Exception {
        List<Pedido> pedidos = pedidoRepository.findAllByUsuario_IdUsuario(idUsuario);

        ArrayList<PedidoDTO> pedidoDtos = new ArrayList<>();

        for(Pedido pedido: pedidos){
            PedidoDTO pedidoDTO = retornarDto(pedido);
            pedidoDtos.add(pedidoDTO);
        }

        return pedidoDtos;
    }

    private List<PedidoXProduto> obterProdutos(ArrayList<ProdutoCarrinhoCreate> produtosCarrinhoCreate, List<Produto> produtosBanco) throws Exception {
        List<PedidoXProduto> produtos = new ArrayList<>();
        for (ProdutoCarrinhoCreate produtoCarrinhoCreate : produtosCarrinhoCreate) {
            Produto produto = produtoService.getById(produtoCarrinhoCreate.getIdProduto());
            produtosBanco.add(produto);
            PedidoXProduto pedidoXProduto = new PedidoXProduto();
            pedidoXProduto.setProduto(produto);
            pedidoXProduto.setQuantidade(produtoCarrinhoCreate.getQuantidade());
            produtos.add(pedidoXProduto);

        }
        return produtos;

    }

    private void emailPedidoCriado(PedidoDTO pedidoDTO, Usuario usuario) throws Exception {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataEntrega = formato.format(pedidoDTO.getDataEntrega());
        String dataPedido = formato.format(pedidoDTO.getDataDePedido());
        String endereco = enderecoService.getMensagemEnderecoEmail(objectMapper.convertValue(pedidoDTO.getEndereco(), Endereco.class));
        String produtos = produtoService.getMensagemProdutoEmail(pedidoDTO.getProdutos());
        String mensagem = String.format("""
                        <span style="display: block; font-size: 16px; font-weight: bold;">Pedido Realizado</span>
                        <section style="color: #ffffff;">
                            <p style="color: #ffffff;">Olá %s, seu pedido foi realizado com sucesso,</p>
                            <p style="color: #ffffff;">O Status atual do seu pedido é %s,</p>
                            <p style="color: #ffffff;">Abaixo seguem detalhes do seu pedido:</p>
                            <p style="color: #ffffff;">Pedido N°: %d,</p>
                            <p style="color: #ffffff;">Total: %s,</p>
                            <p style="color: #ffffff;">Forma de pagamento: %s,</p>
                            <p style="color: #ffffff;">Data do pedido: %s,</p>
                            <p style="color: #ffffff;">Data da entrega: %s,</p>
                            <p style="color: #ffffff;">Produtos: <br> %s </p>
                            <p style="color: #ffffff;">Endereço: <br> %s </p>
                        </section>
                        """,
                usuario.getNome(),
                pedidoDTO.getStatusPedido().toString(),
                pedidoDTO.getIdPedido(),
                pedidoDTO.getTotal(),
                pedidoDTO.getFormaPagamento().toString(),
                dataPedido,
                dataEntrega,
                produtos,
                endereco
        );
        String assunto = "Pedido realizado";
        String destinatario = usuario.getEmail();
        this.emailService.sendEmail(mensagem, assunto, destinatario);
    }

    public PedidoDTO getById(Integer idPedido) throws Exception {
        return retornarDto(findById(idPedido));
    }

    public PedidoDTO retornarDto(Pedido pedido) {
        UsuarioDTO usuarioDTO = new UsuarioDTO(pedido.getUsuario());
        EnderecoDTO enderecoDTO = new EnderecoDTO(pedido.getEndereco());
        CupomDTO cupomDto = new CupomDTO(pedido.getCupom());

        List<PedidoXProduto> pedidoProdutos = pedidoXProdutoRepository.findAllByIdPedido(pedido.getIdPedido());
        List<ProdutoPedidoDTO> produtos = new ArrayList<>();

        for(PedidoXProduto pedidoXProduto : pedidoProdutos){
            ProdutoResponsePedidoDTO produtoDTO = objectMapper.convertValue(pedidoXProduto.getProduto(), ProdutoResponsePedidoDTO.class);

            ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO(produtoDTO, pedidoXProduto.getQuantidade());

            produtos.add(produtoPedidoDTO);
        }

        PedidoDTO pedidoDTO = new PedidoDTO(pedido, usuarioDTO, enderecoDTO, cupomDto, produtos);
        return  pedidoDTO;
    }
}
