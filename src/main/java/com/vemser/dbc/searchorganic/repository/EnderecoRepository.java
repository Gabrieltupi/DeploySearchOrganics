package com.vemser.dbc.searchorganic.repository;


import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EnderecoRepository implements IRepositoryJDBC<Integer, Endereco> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_ENDERECO.NEXTVAL FROM DUAL";

        try (PreparedStatement pstd = connection.prepareStatement(sql);
             ResultSet res = pstd.executeQuery()) {
            if (res.next()) {
                return res.getInt(1);
            }
        }
        return null;
    }

    public Endereco buscarPorId(Integer idEndereco) throws Exception {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM ENDERECO WHERE id_endereco = ?";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setInt(1, idEndereco);

                try (ResultSet rs = pstd.executeQuery()) {
                    if (rs.next()) {
                        Endereco endereco = new Endereco(
                                rs.getString("logradouro"),
                                rs.getString("numero"),
                                rs.getString("complemento"),
                                rs.getString("cep"),
                                rs.getString("cidade"),
                                rs.getString("estado"),
                                rs.getString("pais"),
                                rs.getInt("id_usuario")
                        );
                        endereco.setIdEndereco(rs.getInt("id_endereco"));
                        endereco.setRegiao(rs.getString("regiao"));
                        endereco.setIdUsuario(rs.getInt("id_usuario"));
                        return endereco;
                    }
                }
            }
            throw new Exception("ID " + idEndereco + " não encontrado.");
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            conexaoBancoDeDados.closeConnection(con);
        }
    }

    @Override
    public List<Endereco> listar() throws BancoDeDadosException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM ENDERECO";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                try (ResultSet rs = pstd.executeQuery()) {
                    while (rs.next()) {
                        Endereco endereco = new Endereco(
                                rs.getString("logradouro"),
                                rs.getString("numero"),
                                rs.getString("complemento"),
                                rs.getString("cep"),
                                rs.getString("cidade"),
                                rs.getString("estado"),
                                rs.getString("pais")
                        );
                        endereco.setIdEndereco(rs.getInt("id_endereco"));
                        endereco.setRegiao(rs.getString("regiao"));
                        endereco.setIdUsuario(rs.getInt("id_usuario"));
                        enderecos.add(endereco);
                    }
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            conexaoBancoDeDados.closeConnection(con);
        }
        return enderecos;
    }

    @Override
    public Endereco adicionar(Endereco endereco) throws BancoDeDadosException {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            Integer proximoId = getProximoId(con);

            String sql = "INSERT INTO ENDERECO (id_endereco, logradouro, numero, complemento, cep, cidade, estado, pais, regiao, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setInt(1, proximoId);
                pstd.setString(2, endereco.getLogradouro());
                pstd.setString(3, endereco.getNumero());
                pstd.setString(4, endereco.getComplemento());
                pstd.setString(5, endereco.getCep());
                pstd.setString(6, endereco.getCidade());
                pstd.setString(7, endereco.getEstado());
                pstd.setString(8, endereco.getPais());
                pstd.setString(9, endereco.getRegiao());
                pstd.setInt(10, endereco.getIdUsuario());

                int linhasAfetadas = pstd.executeUpdate();

                if (linhasAfetadas > 0) {
                    endereco.setIdEndereco(proximoId);
                } else {
                    throw new Exception("Endereço não adicionado.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return endereco;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            conexaoBancoDeDados.closeConnection(con);
        }
    }

    @Override
    public Endereco editar(Integer idEndereco, Endereco endereco) throws Exception {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "UPDATE ENDERECO SET logradouro = ?, numero = ?, complemento = ?, cep = ?, cidade = ?, estado = ?, pais = ?, regiao = ? WHERE id_endereco = ?";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setString(1, endereco.getLogradouro());
                pstd.setString(2, endereco.getNumero());
                pstd.setString(3, endereco.getComplemento());
                pstd.setString(4, endereco.getCep());
                pstd.setString(5, endereco.getCidade());
                pstd.setString(6, endereco.getEstado());
                pstd.setString(7, endereco.getPais());
                pstd.setString(8, endereco.getRegiao());
                pstd.setInt(9, idEndereco);

                int linhasAfetadas = pstd.executeUpdate();

                if (linhasAfetadas > 0) {
                    return endereco;
                } else {
                    throw new Exception("Endereço não adicionado.");
                }
            }
        } catch (BancoDeDadosException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            conexaoBancoDeDados.closeConnection(con);
        }
    }

    @Override
    public Boolean remover(Integer idEndereco) throws BancoDeDadosException {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM ENDERECO WHERE id_endereco = ?";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setInt(1, idEndereco);

                int linhasAfetadas = pstd.executeUpdate();

                if (linhasAfetadas > 0) {
                    return true;
                }
                throw new Exception("Nenhum endereço removido.");
            }
        } catch (Exception e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            conexaoBancoDeDados.closeConnection(con);
        }
    }

    public List<Endereco> listarPorUsuario(Integer idUsuario) throws BancoDeDadosException {
        List<Endereco> enderecos = new ArrayList<>();
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM ENDERECO WHERE id_usuario = ?";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setInt(1, idUsuario);

                try (ResultSet rs = pstd.executeQuery()) {
                    while (rs.next()) {
                        Endereco endereco = new Endereco(
                                rs.getString("logradouro"),
                                rs.getString("numero"),
                                rs.getString("complemento"),
                                rs.getString("cep"),
                                rs.getString("cidade"),
                                rs.getString("estado"),
                                rs.getString("pais"),
                                rs.getInt("id_usuario")
                        );
                        endereco.setIdEndereco(rs.getInt("id_endereco"));
                        endereco.setRegiao(rs.getString("regiao"));
                        endereco.setIdUsuario(rs.getInt("id_usuario"));
                        enderecos.add(endereco);
                    }
                    return enderecos;
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            conexaoBancoDeDados.closeConnection(con);
        }
    }

    public Boolean removerPorUsuario(Integer idUsuario) throws BancoDeDadosException {
        Connection con = null;

        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "DELETE FROM ENDERECO WHERE id_usuario = ?";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setInt(1, idUsuario);

                int linhasAfetadas = pstd.executeUpdate();

                if (linhasAfetadas > 0) {
                    return true;
                }

                throw new Exception("Nenhum endereço removido.");
            }
        } catch (Exception e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            conexaoBancoDeDados.closeConnection(con);
        }
    }
}

