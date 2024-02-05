package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco,Integer> {
    @Query("SELECT e FROM ENDERECO e WHERE e.usuario.idUsuario = :idUsuario")
    List<Endereco> findAllByUsuarioIdUsuario(Integer idUsuario);
}



