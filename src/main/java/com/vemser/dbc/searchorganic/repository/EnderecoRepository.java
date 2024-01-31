package com.vemser.dbc.searchorganic.repository;


import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Endereco;
import feign.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {

    List<Endereco> findByUsuarioIdUsuario(Integer idUsuario);

    @Query("SELECT e FROM ENDERECO e WHERE e.usuario.idUsuario = :idUsuario")
    List<Endereco> findAllByUsuarioIdUsuario(Integer idUsuario);
}



