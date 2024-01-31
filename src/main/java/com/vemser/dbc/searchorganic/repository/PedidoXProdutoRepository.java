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

public interface PedidoXProdutoRepository extends JpaRepository<com.vemser.dbc.searchorganic.model.PedidoXProduto, ProdutoXPedidoPK> {
    @Query("SELECT pxp FROM PEDIDOXPRODUTO pxp WHERE pxp.produtoXPedidoPK.idPedido = :idPedido")
    List<PedidoXProduto> findByPedidoId(@Param("idPedido") Integer idPedido);

    @Modifying
    @Transactional
    @Query("UPDATE Produto p " +
            "SET p.quantidade = :quantidade " +
            "WHERE p.idProduto = :idProduto")
    void atualizarQuantidadeDoProduto(@Param("idProduto") Integer idProduto, @Param("quantidade") BigDecimal quantidade);
}
