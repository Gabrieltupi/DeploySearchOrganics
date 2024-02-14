package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RelatorioRepository extends JpaRepository<Produto, Integer> {
    @Query(value = "SELECT NOME, PRECO FROM PRODUTO", nativeQuery = true)
    Page<Object[]> findAllProdutosByPreco(Pageable pageable);

    @Query(value = "SELECT NOME, QUANTIDADE FROM PRODUTO", nativeQuery = true)
    Page<Object[]> findAllProdutosByQuantidade(Pageable pageable);

    @Query(value = "SELECT p.nome, COUNT(pp.produto) as pedidos " +
            "FROM PEDIDOXPRODUTO pp " +
            "JOIN pp.produto p " +
            "GROUP BY p.nome ")
    Page<Object[]> findAllProdutosByPedidos(Pageable pageable);

    @Query(value = "SELECT p.nome, TO_CHAR(pp.pedido.dataDePedido, 'MM') as mes, COUNT(pp.produto) as pedidos " +
            "FROM PEDIDOXPRODUTO pp " +
            "JOIN pp.produto p " +
            "GROUP BY p.nome, TO_CHAR(pp.pedido.dataDePedido, 'MM')")
    Page<Object[]> findAllProdutosByPedidosByMes(Pageable pageable);
}