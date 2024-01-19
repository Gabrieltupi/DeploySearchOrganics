package com.vemser.dbc.searchorganic.repository;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IRepositoryJDBC<ARGUMENTO, OBJETO> {
    Integer getProximoId(Connection connection) throws SQLException;

    OBJETO adicionar(OBJETO object) throws BancoDeDadosException, BancoDeDadosException;

    boolean remover(ARGUMENTO id) throws BancoDeDadosException;

    boolean editar(ARGUMENTO id, OBJETO objeto) throws BancoDeDadosException;

    List<OBJETO> listar() throws BancoDeDadosException;
}