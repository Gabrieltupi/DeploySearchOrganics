package com.vemser.dbc.searchorganic.repository;


import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {

    Usuario findByLogin(String login);
}
