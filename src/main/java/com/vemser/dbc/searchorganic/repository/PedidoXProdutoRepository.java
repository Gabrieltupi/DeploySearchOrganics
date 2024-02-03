package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.PedidoXProduto;
import com.vemser.dbc.searchorganic.model.pk.ProdutoXPedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface PedidoXProdutoRepository extends JpaRepository<PedidoXProduto, ProdutoXPedidoPK> {
    @Query(value = "SELECT * FROM PEDIDOXPRODUTO pxp WHERE pxp.id_pedido = :idPedido", nativeQuery = true)
    List<PedidoXProduto> findAllByIdPedido(@Param("idPedido") Integer idPedido);

    @Modifying
    @Transactional
    @Query(value = "UPDATE PRODUTO SET quantidade = :quantidade WHERE id_produto = :idProduto", nativeQuery = true)
    void updateQuantidadeProduto(@Param("idProduto") Integer idProduto, @Param("quantidade") BigDecimal quantidade);
}
