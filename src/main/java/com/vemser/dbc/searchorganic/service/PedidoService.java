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

    public PedidoDTO adicionar(Integer id, PedidoCreateDTO pedidoCreateDTO) throws Exception {
        Pedido pedido = objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
        List<Produto> produtosBanco = new ArrayList<>();
        List<PedidoXProduto> produtos = obterProdutos(pedidoCreateDTO.getProdutosCarrinho(), produtosBanco);

        Usuario usuario = usuarioService.obterUsuarioPorId(id);
        Endereco endereco = enderecoService.getById(pedidoCreateDTO.getIdEndereco());

        pedido.setEndereco(endereco);
        pedido.setUsuario(usuario);

        for (IValidarPedido validador : validarPedidoList) {
            validador.validar(pedido, id, produtos);
        }


        if (pedidoCreateDTO.getIdCupom() != null) {
            Cupom cupom = cupomService.buscarCupomPorId(pedidoCreateDTO.getIdCupom());
            pedido.setCupom(cupom);
        }

        pedido = pedidoRepository.save(pedido);

        for (PedidoXProduto pedidoXProduto : produtos) {
            pedidoXProduto.setPedido(pedido);
            Produto produto = pedidoXProduto.getProduto();
            pedidoXProduto.setProdutoXPedidoPK(new ProdutoXPedidoPK(produto.getIdProduto(), pedido.getIdPedido()));
            pedidoXProdutoRepository.save(pedidoXProduto);
            BigDecimal quantidadeAtualizada = produto.getQuantidade().subtract(BigDecimal.valueOf(pedidoXProduto.getQuantidade()));
            pedidoXProdutoRepository.atualizarQuantidadeDoProduto(produto.getIdProduto(), quantidadeAtualizada);
        }


        PedidoDTO pedidoDTO = this.retornarDTO(pedido);


        this.emailPedidoCriado(pedidoDTO, usuario);

        return pedidoDTO;
    }

    private List<PedidoXProduto> obterProdutos(ArrayList<ProdutoCarrinhoCreate> produtosCarrinhoCreate, List<Produto> produtosBanco) throws Exception {
                List<PedidoXProduto> produtos = new ArrayList<>();
            for (ProdutoCarrinhoCreate produtoCarrinhoCreate : produtosCarrinhoCreate) {
                Produto produto = produtoService.buscarProdutoPorId(produtoCarrinhoCreate.getIdProduto());
                produtosBanco.add(produto);
                PedidoXProduto pedidoXProduto = new PedidoXProduto();
                pedidoXProduto.setProduto(produto);
                pedidoXProduto.setQuantidade(produtoCarrinhoCreate.getQuantidade());
                produtos.add(pedidoXProduto);

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
    public PedidoDTO buscaPorId(Integer idPedido) throws Exception {

        Pedido pedido = obterPorId(idPedido);
        System.out.println(pedido);
        return retornarDTO(pedido);
    }

    public void excluir(Integer idPedido) throws Exception {
        Pedido pedido = obterPorId(idPedido);
        List<PedidoXProduto> produtos = pedidoXProdutoRepository.findByPedidoId(pedido.getIdPedido());
        for (PedidoXProduto pedidoXProduto : produtos) {
            Produto produto = pedidoXProduto.getProduto();

            produto.setQuantidade(produto.getQuantidade().add(BigDecimal.valueOf(pedidoXProduto.getQuantidade())));

            produtoRepository.save(produto);
        }

        pedidoRepository.cancelarPedido(idPedido);
    }

    public PedidoDTO retornarDTO(Pedido pedido) throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO(pedido.getUsuario());
        EnderecoDTO enderecoDTO = new EnderecoDTO(pedido.getEndereco());
        CupomDto cupomDto = new CupomDto(pedido.getCupom());
        List<PedidoXProduto> produtos = pedidoXProdutoRepository.findByPedidoId(pedido.getIdPedido());
        PedidoDTO pedidoDTO = new PedidoDTO(pedido, usuarioDTO, enderecoDTO, cupomDto, produtos);
        return  pedidoDTO;
    }


    public PedidoDTO atualizarPedido(Integer id, PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = obterPorId(id);
        Endereco endereco = enderecoService.getById(pedidoAtualizar.getIdEndereco());

        pedidoEntity.setEndereco(endereco);

        pedidoEntity.setFormaPagamento(pedidoAtualizar.getFormaPagamento());
        pedidoEntity.setDataEntrega(pedidoAtualizar.getDataEntrega());
        pedidoEntity.setStatusPedido(pedidoAtualizar.getStatusPedido());
        pedidoEntity.setPrecoFrete(pedidoAtualizar.getPrecoFrete());

        pedidoRepository.save(pedidoEntity);

        return retornarDTO(pedidoEntity);
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

    public ArrayList<PedidoDTO> preencherInformacoesArray(List<Pedido> pedidos) throws Exception {
        ArrayList<PedidoDTO> pedidoDTOS = new ArrayList<>();
        for(Pedido pedido: pedidos){
            PedidoDTO pedidoDTO = retornarDTO(pedido);
            pedidoDTOS.add(pedidoDTO);
        }
        return pedidoDTOS;
    }
}
