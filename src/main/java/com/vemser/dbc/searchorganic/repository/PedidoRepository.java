package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findAllByUsuario_IdUsuario(Integer idUsuario);

    @Modifying
    @Query("UPDATE PEDIDO p SET p.statusPedido = 'CANCELADO' WHERE p.idPedido = :idPedido")
    void cancelarPedido(@Param("idPedido") Integer idPedido);
}
