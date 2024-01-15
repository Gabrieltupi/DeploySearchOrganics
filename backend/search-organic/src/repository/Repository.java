package repository;
import exceptions.BancoDeDadosException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface Repository<ARGUMENTO, OBJETO> {
    Integer getProximoId(Connection connection) throws SQLException;

    OBJETO adicionar(OBJETO object) throws BancoDeDadosException;

    boolean remover(ARGUMENTO id) throws BancoDeDadosException;

    boolean editar(ARGUMENTO id, OBJETO objeto) throws BancoDeDadosException;

    List<OBJETO> listar() throws BancoDeDadosException;
}