package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoCreateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoUpdateDTO;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
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
        for(IValidarPedido validador: validarPedidoList){
            validador.validar(pedidoCreateDTO, id);
        }

        Usuario usuario = usuarioService.obterUsuarioPorId(id);
        Pedido pedido = objectMapper.convertValue(pedidoCreateDTO, Pedido.class);
        pedido.setIdUsuario(id);



        pedido = pedidoRepository.adicionar(pedido);
        PedidoDTO pedidoDTO = this.preencherInformacoes(pedido);

        this.emailPedidoCriado(pedidoDTO, usuario);

        return pedidoDTO;
    }

    private void emailPedidoCriado(PedidoDTO pedidoDTO, Usuario usuario) throws Exception {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataEntrega = formato.format(pedidoDTO.getDataEntrega());
        String dataPedido = formato.format(pedidoDTO.getDataDePedido());
        String endereco = enderecoService.getMensagemEnderecoEmail(pedidoDTO.getEndereco());
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


    public List<Pedido> obterPedidoPorIdUsuario(Integer idUsuario) throws Exception {
        return this.pedidoRepository.obterPedidoPorIdUsuario(idUsuario);
    }

    public Pedido obterPorId(Integer id) throws Exception {
        return pedidoRepository.buscaPorId(id);
    }

    public void excluir(int idPedido) throws Exception {
        pedidoRepository.remover(idPedido);
    }

    public PedidoDTO preencherInformacoes(Pedido pedido) throws Exception {
        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setIdPedido(pedido.getIdPedido());
        pedidoDTO.setIdUsuario(pedido.getIdUsuario());
        pedidoDTO.setFormaPagamento(pedido.getFormaPagamento());
        pedidoDTO.setTotal(pedido.getPrecoCarrinho().add(pedido.getPrecoFrete()));
        pedidoDTO.setStatusPedido(pedido.getStatusPedido());
        pedidoDTO.setDataDePedido(pedido.getDataDePedido());
        pedidoDTO.setDataEntrega(pedido.getDataEntrega());
        pedidoDTO.setProdutos(pedido.getProdutos());
        pedidoDTO.setCupom(cupomService.buscarCupomPorId(pedido.getIdCupom()));
        pedidoDTO.setPrecoFrete(pedido.getPrecoFrete());
        pedidoDTO.setPrecoCarrinho(pedido.getPrecoCarrinho());
        pedidoDTO.setProdutos(pedidoRepository.listarProdutosDoPedido(pedido.getIdPedido()));

        EnderecoDTO enderecoDTO = enderecoService.buscarEndereco(pedido.getIdEndereco());
        Endereco endereco = objectMapper.convertValue(enderecoDTO, Endereco.class);
        pedidoDTO.setEndereco(endereco);

        return pedidoDTO;
    }

    public ArrayList<PedidoDTO> preencherInformacoesArray(List<Pedido> pedidos) throws Exception {
        ArrayList<PedidoDTO> pedidosDTO = new ArrayList<>();
        for (Pedido pedido : pedidos) {
            PedidoDTO pedidoDTO = this.preencherInformacoes(pedido);
            pedidosDTO.add(pedidoDTO);
        }
        return pedidosDTO;
    }

    public Pedido atualizarPedido(Integer id, PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = obterPorId(id);
        enderecoService.buscarEndereco(pedidoAtualizar.getIdEndereco());

        pedidoEntity.setIdEndereco(pedidoEntity.getIdEndereco());
        pedidoEntity.setFormaPagamento(pedidoAtualizar.getFormaPagamento());
        pedidoEntity.setDataEntrega(pedidoAtualizar.getDataEntrega());
        pedidoEntity.setStatusPedido(pedidoAtualizar.getStatusPedido());
        pedidoEntity.setPrecoFrete(pedidoAtualizar.getPrecoFrete());
        pedidoEntity.setTipoEntrega(pedidoAtualizar.getTipoEntrega());
        pedidoRepository.editar(pedidoEntity.getIdPedido(), pedidoEntity);
        return pedidoEntity;
    }
}
