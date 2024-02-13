package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Endereco;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    @Query("SELECT e FROM ENDERECO e WHERE e.usuario.idUsuario = :idUsuario")
    List<Endereco> findAllByUsuarioIdUsuario(Integer idUsuario);


    @Query(value = "SELECT COUNT(*) FROM USUARIO_CARGO uc2 \n" +
            "WHERE uc2.ID_USUARIO = :userId AND uc2.ID_CARGO = 1", nativeQuery = true)
    Integer existsAdminCargoByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT ID_ENDERECO FROM ENDERECO WHERE ID_USUARIO = :userId", nativeQuery = true)
    Optional<Integer> findEnderecoIdByUserId(@Param("userId") Integer userId);


}



