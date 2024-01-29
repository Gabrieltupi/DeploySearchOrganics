package com.vemser.dbc.searchorganic.repository;


import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.service.EnderecoService;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UsuarioRepository implements IRepositoryJDBC<Integer, Usuario> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;
    private final EnderecoRepository enderecoRepository;

    @Override
    public Integer getProximoId(Connection con) throws SQLException {
        String sql = "SELECT SEQ_USUARIO.nextval mysequence from DUAL";
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    public Usuario buscaPorLogin(String login) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM Usuario WHERE LOGIN = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, login);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(res.getInt("ID_USUARIO"));
                usuario.setLogin(res.getString("LOGIN"));
                usuario.setSenha(res.getString("SENHA"));
                usuario.setCpf(res.getString("CPF"));
                usuario.setTipoAtivo(TipoAtivo.fromString(res.getString("ATIVO")));
                usuario.setNome(res.getString("NOME"));
                usuario.setSobrenome(res.getString("SOBRENOME"));
                usuario.setEmail(res.getString("EMAIL"));
                usuario.setDataNascimento(res.getDate("DATANASCIMENTO").toLocalDate());

                List<Endereco> enderecos = enderecoRepository.listarPorUsuario(usuario.getIdUsuario());
                usuario.setEnderecos(enderecos);

                return usuario;
            }

            throw new RuntimeException("Usuário não encontrado com o login: " + login);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Usuario adicionar(Usuario usuario) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            usuario.setIdUsuario(proximoId);

            String sql = "INSERT INTO USUARIO\n" +
                    "(ID_USUARIO, LOGIN, SENHA, CPF, ATIVO, NOME, SOBRENOME, EMAIL, DATANASCIMENTO)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)\n";


            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, usuario.getIdUsuario());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getCpf());
            stmt.setString(5, usuario.getTipoAtivo().toString());
            stmt.setString(6, usuario.getNome());
            stmt.setString(7, usuario.getSobrenome());
            stmt.setString(8, usuario.getEmail());
            stmt.setDate(9, Date.valueOf(usuario.getDataNascimento()));

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Usuário adicionado com sucesso!");
            } else {
                System.out.println("Ocorreu um erro ao adicionar");
            }

            List<Endereco> enderecos = enderecoRepository.listarPorUsuario(usuario.getIdUsuario());
            usuario.setEnderecos(enderecos);

            return usuario;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Boolean remover(Integer id) throws BancoDeDadosException {

        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE Usuario SET ATIVO='N' WHERE id_usuario = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int res = stmt.executeUpdate();
                if (res > 0) {
                    System.out.println("Usuário removido com sucesso!");
                    return true;
                } else {
                    System.out.println("Não foi possível remover o usuário. Usuário não encontrado.");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Usuario editar(Integer id, Usuario usuario) throws Exception {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE Usuario SET LOGIN = ?, SENHA = ?, CPF = ?, ATIVO = ?, NOME = ?, SOBRENOME = ?, EMAIL = ?, DATANASCIMENTO = ? WHERE ID_USUARIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getCpf());
            stmt.setString(4, usuario.getTipoAtivo().toString());
            stmt.setString(5, usuario.getNome());
            stmt.setString(6, usuario.getSobrenome());
            stmt.setString(7, usuario.getEmail());
            stmt.setDate(8, Date.valueOf(usuario.getDataNascimento()));
            stmt.setInt(9, id);

            int res = stmt.executeUpdate();
            if (res > 0) {
                List<Endereco> enderecos = enderecoRepository.listarPorUsuario(id);
                usuario.setIdUsuario(id);
                usuario.setEnderecos(enderecos);

                return usuario;
            }
            throw new Exception("Usuário não atualizado.");
        } catch (Exception e) {
            throw new Exception("Usuário não atualizado: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Usuario> listar() throws BancoDeDadosException {
        List<Usuario> usuarios = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM Usuario";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(res.getInt("ID_USUARIO"));
                usuario.setLogin(res.getString("LOGIN"));
                usuario.setSenha(res.getString("SENHA"));
                usuario.setCpf(res.getString("CPF"));
                usuario.setTipoAtivo(TipoAtivo.fromString(res.getString("ATIVO")));
                usuario.setNome(res.getString("NOME"));
                usuario.setSobrenome(res.getString("SOBRENOME"));
                usuario.setEmail(res.getString("EMAIL"));
                usuario.setDataNascimento(res.getDate("DATANASCIMENTO").toLocalDate());

                List<Endereco> enderecos = enderecoRepository.listarPorUsuario(usuario.getIdUsuario());
                usuario.setEnderecos(enderecos);

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return usuarios;
    }

    public Usuario buscaPorId(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM Usuario WHERE ID_USUARIO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(res.getInt("ID_USUARIO"));
                usuario.setLogin(res.getString("LOGIN"));
                usuario.setSenha(res.getString("SENHA"));
                usuario.setCpf(res.getString("CPF"));
                usuario.setTipoAtivo(TipoAtivo.fromString(res.getString("ATIVO")));
                usuario.setNome(res.getString("NOME"));
                usuario.setSobrenome(res.getString("SOBRENOME"));
                usuario.setEmail(res.getString("EMAIL"));
                usuario.setDataNascimento(res.getDate("DATANASCIMENTO").toLocalDate());

                List<Endereco> enderecos = enderecoRepository.listarPorUsuario(usuario.getIdUsuario());
                usuario.setEnderecos(enderecos);

                return usuario;
            }

            throw new RegraDeNegocioException("Usuário não encontrado");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BancoDeDadosException(e.getCause());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
