package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.repository.PedidoRepository;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ObjectMapper objectMapper;
    private final EnderecoService enderecoService;
    private final CupomService cupomService;

    public PedidoService(PedidoRepository pedidoRepository, ObjectMapper objectMapper, EnderecoService enderecoService, CupomService cupomService){
        this.pedidoRepository = pedidoRepository;
        this.objectMapper = objectMapper;
        this.enderecoService = enderecoService;
        this.cupomService = cupomService;
    }

    public Pedido adicionar(Pedido pedido) throws Exception {
        return pedidoRepository.adicionar(pedido);
    }

    public List<PedidoDTO> obterPedidoPorIdUsuario(Integer idUsuario) throws Exception {
        List<Pedido> pedidos = this.pedidoRepository.obterPedidoPorIdUsuario(idUsuario);
        List<PedidoDTO> pedidosDTO = new ArrayList<>();
        for(Pedido pedido: pedidos){
            PedidoDTO pedidoDTO = this.preencherInformacoes(pedido);
            pedidosDTO.add(pedidoDTO);
        }
        return pedidosDTO;
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
        pedidoDTO.setEndereco(enderecoService.getEndereco(pedido.getIdEndereco()));
        pedidoDTO.setProdutos(pedido.getProdutos());
        pedidoDTO.setCupom(cupomService.buscarCupomPorId(pedido.getIdCupom()));
        pedidoDTO.setPrecoFrete(pedido.getPrecoFrete());
        pedidoDTO.setPrecoCarrinho(pedido.getPrecoCarrinho());
        pedidoDTO.setProdutos(pedidoRepository.listarProdutosDoPedido(pedido.getIdPedido()));
        return pedidoDTO;
    }

    public Pedido atualizarPedido(Integer id, PedidoUpdateDTO pedidoAtualizar) throws Exception {
        Pedido pedidoEntity = obterPorId(id);
        enderecoService.getEndereco(pedidoAtualizar.getIdEndereco());
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
