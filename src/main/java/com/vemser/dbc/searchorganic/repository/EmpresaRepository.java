package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Empresa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmpresaRepository{
    private final ConexaoBancoDeDados conexaoBancoDeDados;
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_EMPRESA.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    public Empresa adicionar(Empresa empresa, Integer idUsuario) throws BancoDeDadosException {
        try (Connection con = conexaoBancoDeDados.getConnection()) {
            String sqlVerificarUsuario = "SELECT ATIVO FROM USUARIO WHERE ID_USUARIO = ?";
            try (PreparedStatement pstd = con.prepareStatement(sqlVerificarUsuario)) {
                pstd.setInt(1, idUsuario);
                try (ResultSet resp = pstd.executeQuery()) {
                    if (resp.next()) {
                        String ativo = resp.getString("ATIVO");
                        if (ativo.equalsIgnoreCase("N")) {
                            System.err.println("Usuário desativado");
                            return empresa;
                        }
                    } else {
                        System.err.println("Usuário não cadastrado");
                        return empresa;
                    }
                }
            }

            Integer proximoId = getProximoId(con);
            empresa.setIdEmpresa(proximoId);

            String sql = "INSERT INTO EMPRESA (ID_EMPRESA, ID_USUARIO, NOMEFANTASIA, CNPJ, RAZAOSOCIAL, INSCRICAOESTADUAL, SETOR)\n" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, empresa.getIdEmpresa());
                stmt.setInt(2, idUsuario);
                stmt.setString(3, empresa.getNomeFantasia());
                stmt.setString(4, empresa.getCnpj());
                stmt.setString(5, empresa.getRazaoSocial());
                stmt.setString(6, empresa.getInscricaoEstadual());
                stmt.setString(7, empresa.getSetor());

                int res = stmt.executeUpdate();
                if (res > 0) {
                    System.out.println("Empresa adicionada");
                } else {
                    System.out.println("Ocorreu um erro ao adicionar");
                }
            }

            return empresa;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }


    public Boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM EMPRESA WHERE id_empresa = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Empresa removida com sucesso");
                return true;
            } else {
                System.out.println("Ocorreu um erro ao remover");
                return false;
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



    public Empresa editar(Integer id, Empresa empresaAtualizada) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE EMPRESA SET " +
                    " NOMEFANTASIA = ?," +
                    " CNPJ = ?," +
                    " RAZAOSOCIAL = ?, " +
                    " INSCRICAOESTADUAL = ?, " +
                    " SETOR = ? " +
                    " WHERE ID_EMPRESA = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, empresaAtualizada.getNomeFantasia());
            stmt.setString(2, empresaAtualizada.getCnpj());
            stmt.setString(3, empresaAtualizada.getRazaoSocial());
            stmt.setString(4, empresaAtualizada.getInscricaoEstadual());
            stmt.setString(5, empresaAtualizada.getSetor());
            stmt.setInt(6, empresaAtualizada.getIdEmpresa());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("Empresa atualizada com sucesso");
                return empresaAtualizada;
            }
            throw new Exception("Empresa não atualizada.");
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
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


    public List<Empresa> listar() throws BancoDeDadosException {
        List<Empresa> empresas = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM EMPRESA";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(res.getInt("ID_EMPRESA"));
                empresa.setNomeFantasia(res.getString("NOMEFANTASIA"));
                empresa.setCnpj(res.getString("CNPJ"));
                empresa.setRazaoSocial(res.getString("RAZAOSOCIAL"));
                empresa.setInscricaoEstadual(res.getString("INSCRICAOESTADUAL"));
                empresa.setSetor(res.getString("SETOR"));
                empresa.setIdUsuario(res.getInt("ID_USUARIO"));
                empresas.add(empresa);
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
        return empresas;
    }

    public Empresa buscaPorId(Integer id) throws BancoDeDadosException, RegraDeNegocioException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM EMPRESA WHERE id_empresa = ?";

            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setInt(1, id);

                try (ResultSet res = pstd.executeQuery()) {
                    if (res.next()) {
                        Empresa empresa = new Empresa();
                        empresa.setIdEmpresa(res.getInt("ID_EMPRESA"));
                        empresa.setNomeFantasia(res.getString("NOMEFANTASIA"));
                        empresa.setCnpj(res.getString("CNPJ"));
                        empresa.setRazaoSocial(res.getString("RAZAOSOCIAL"));
                        empresa.setInscricaoEstadual(res.getString("INSCRICAOESTADUAL"));
                        empresa.setSetor(res.getString("SETOR"));
                        empresa.setIdUsuario(res.getInt("ID_USUARIO"));
                        return empresa;
                    }
                    throw new RegraDeNegocioException("Empresa não encontrada");

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
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

}