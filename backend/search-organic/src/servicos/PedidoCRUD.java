package servicos;

import modelo.Pedido;
import modelo.Produto;
import utils.FormaPagamento;

import java.util.ArrayList;

public class PedidoCRUD {
    private ArrayList<Pedido> pedidos = new java.util.ArrayList<>();

    public void criarPedido(ArrayList<Produto> produtos, FormaPagamento formaPagamento, int consumidorId){
        Pedido pedido = new Pedido(produtos, formaPagamento, consumidorId);
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
