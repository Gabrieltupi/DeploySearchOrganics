package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findAllByUsuario_IdUsuario(Integer idUsuario);

    @Modifying
    @Query("UPDATE PEDIDO p SET p.statusPedido = 'CANCELADO' WHERE p.idPedido = :idPedido")
    void cancelarPedido(@Param("idPedido") Integer idPedido);

    @Query(value = "SELECT COUNT(*) FROM USUARIO_CARGO uc2 \n" +
            "WHERE uc2.ID_USUARIO = :userId AND uc2.ID_CARGO = 1", nativeQuery = true)
    Integer existsAdminCargoByUserId(@feign.Param("userId") Integer userId);


    @Query(value = "SELECT COUNT(*) FROM USUARIO_CARGO uc2 \n" +
            "WHERE uc2.ID_USUARIO = :userId AND uc2.ID_CARGO = 3", nativeQuery = true)
    Integer existsEmpresaCargoByUserId(@feign.Param("userId") Integer userId);

    @Query(value = "SELECT ID_PEDIDO FROM PEDIDO WHERE ID_USUARIO = :userId", nativeQuery = true)
    Optional<Integer> findUsuarioIdByUserId(@feign.Param("userId") Integer userId);

    @Query(value = "SELECT P.ID_PEDIDO FROM PEDIDO P\n" +
            "LEFT JOIN EMPRESA E ON P.ID_EMPRESA =E.ID_EMPRESA \n" +
            "WHERE E.ID_USUARIO= :userId", nativeQuery = true)
    List<Integer> findEmpresaIdByUserId(@feign.Param("userId") Integer userId);



}
