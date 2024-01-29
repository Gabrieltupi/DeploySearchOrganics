package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.cupom.CupomDto;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoUpdateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.ProdutoCarrinhoCreate;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.repository.PedidoRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;
    private final EnderecoService enderecoService;
    private final CupomService cupomService;
    private final UsuarioService usuarioService;
    private final EmailService emailService;
    private final ProdutoService produtoService;
    private final List<IValidarPedido> validarPedidoList;

    public PedidoDTO adicionar(Integer id, PedidoCreateDTO pedidoCreateDTO) throws Exception {
        Pedido pedido = objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
        pedido.setProdutos(obterProdutos(pedidoCreateDTO.getProdutosCarrinho()));

        for (IValidarPedido validador : validarPedidoList) {
            validador.validar(pedido, id);
        }

        Usuario usuario = usuarioService.obterUsuarioPorId(id);
        Endereco endereco = enderecoService.obterEndereco(pedidoCreateDTO.getIdEndereco());

        pedido.setEndereco(endereco);
        pedido.setUsuario(usuario);

        if (pedidoCreateDTO.getIdCupom() != null) {
            Cupom cupom = cupomService.buscarCupomPorId(pedidoCreateDTO.getIdCupom());
            pedido.setCupom(cupom);
        }

        pedido = pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = this.preencherInformacoes(pedido);

        this.emailPedidoCriado(pedidoDTO, usuario);

        return pedidoDTO;
    }

    private List<ProdutoCarrinho> obterProdutos(ArrayList<ProdutoCarrinhoCreate> produtosCarrinhoCreate) throws Exception {

              List<ProdutoCarrinho>  produtos = new ArrayList<>();

            for (ProdutoCarrinhoCreate produtoCarrinhoCreate : produtosCarrinhoCreate) {
                Produto produto = produtoService.buscarProdutoPorId(produtoCarrinhoCreate.getIdProduto());
                ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();

                produtoCarrinho.setProduto(produto);
                produtoCarrinho.setQuantidade(produtoCarrinhoCreate.getQuantidade());
                produtoCarrinho.setPedido(null);

                produtos.add(produtoCarrinho);
            }



            return produtos;

    }


    public List<Pedido> obterPedidoPorIdUsuario(Integer idUsuario) throws Exception {
        return this.pedidoRepository.findAllByUsuario_IdUsuario(idUsuario);
    }

    public Pedido obterPorId(Integer id) throws Exception {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Pedido não encontrado: " + id));
    }

    public void excluir(Integer idPedido) throws Exception {
        Pedido pedido = obterPorId(idPedido);
        for (ProdutoCarrinho produtoCarrinho : pedido.getProdutos()) {
            Produto produto = produtoCarrinho.getProduto();

            produto.setQuantidade(produto.getQuantidade().add(BigDecimal.valueOf(produtoCarrinho.getQuantidade())));

            produtoRepository.save(produto);
        }

        pedidoRepository.cancelarPedido(idPedido);
    }

    public PedidoDTO preencherInformacoes(Pedido pedido) throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();

        pedidoDTO.setIdPedido(pedido.getIdPedido());

        pedidoDTO.setUsuario(objectMapper.convertValue(pedido.getUsuario(), UsuarioDTO.class));

        pedidoDTO.setFormaPagamento(pedido.getFormaPagamento());
        pedidoDTO.setTotal(pedido.getPrecoCarrinho().add(pedido.getPrecoFrete()));
        pedidoDTO.setStatusPedido(pedido.getStatusPedido());
        pedidoDTO.setDataDePedido(pedido.getDataDePedido());
        pedidoDTO.setDataEntrega(pedido.getDataEntrega());
        pedidoDTO.setProdutos(pedido.getProdutos());

        pedidoDTO.setCupom(objectMapper.convertValue(pedido.getCupom(), CupomDto.class));

        pedidoDTO.setPrecoFrete(pedido.getPrecoFrete());
        pedidoDTO.setPrecoCarrinho(pedido.getPrecoCarrinho());

        pedidoDTO.setEndereco(objectMapper.convertValue(pedido.getEndereco(), EnderecoDTO.class));

        return pedidoDTO;
    }


    public Pedido atualizarPedido(Integer id, PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = obterPorId(id);
        Endereco endereco = enderecoService.obterEndereco(pedidoAtualizar.getIdEndereco());

        pedidoEntity.setEndereco(endereco);

        pedidoEntity.setFormaPagamento(pedidoAtualizar.getFormaPagamento());
        pedidoEntity.setDataEntrega(pedidoAtualizar.getDataEntrega());
        pedidoEntity.setStatusPedido(pedidoAtualizar.getStatusPedido());
        pedidoEntity.setPrecoFrete(pedidoAtualizar.getPrecoFrete());

        pedidoRepository.save(pedidoEntity);

        return pedidoEntity;
    }


    public ArrayList<PedidoDTO> preencherInformacoesArray(List<Pedido> pedidos) throws Exception {
        ArrayList<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = this.preencherInformacoes(pedido);
            pedidosDTO.add(pedidoDTO);
        }
        return pedidosDTO;
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
}
