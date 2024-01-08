package servicos;

import modelo.Cupom;
import modelo.Endereco;
import modelo.Pedido;
import modelo.Produto;
import utils.FormaPagamento;
import utils.validadores.TipoEntrega;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class PedidoCRUD {
    private ArrayList<Pedido> pedidos = new java.util.ArrayList<>();

    public void criarPedido(){}

    public void adicionarPedido(Pedido pedido){
        pedidos.add(pedido);
    }

    public void criarPedido(Pedido pedido){
        this.pedidos.add(pedido);
    }

    public void listarPedidos() {
        for (Pedido pedido : pedidos) {
            pedido.imprimir();
        }
    }

    public void atualizarPedido(int idPedido, Pedido pedidoAtualizado) {
        if(idPedido > this.pedidos.size()){
            System.out.println("ID de pedido inválido");
        }
        Pedido pedido = this.pedidos.get(idPedido);
        pedido.setEntregue(pedidoAtualizado.getEntregue());
        pedido.setProdutos(pedidoAtualizado.getProdutos());
        pedido.setTotal(pedidoAtualizado.getTotal());
        pedido.setFormaPagamento(pedidoAtualizado.getFormaPagamento());
    }

    public void excluirPedido(int idPedido) {
        if(idPedido > this.pedidos.size()){
            System.out.println("ID de pedido inválido");
        }
        this.pedidos.remove(idPedido);
    }

}
