package repository;

import exceptions.BancoDeDadosException;
import exceptions.EmpresaNaoEncontradaException;
import modelo.Empresa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaRepository implements Repository<Integer, Empresa>{
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_EMPRESA.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Empresa adicionar(Empresa empresa) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

             String sqlVerificarUsuario = "SELECT ATIVO FROM USUARIO WHERE ID_USUARIO = ?";
             PreparedStatement pstd = con.prepareStatement(sqlVerificarUsuario);
             pstd.setInt(1, empresa.getIdUsuario());
             ResultSet resp = pstd.executeQuery();
             int resultados = 0;

             while (resp.next()) {
                String ativo = resp.getString("ATIVO");
                if(ativo.equalsIgnoreCase("N")){
                    System.err.println("Usuario desativado");
                    return empresa;
                }
                resultados++;
            }

            if(resultados == 0) {
                System.err.println("Usuario não cadastrado");
                return empresa;
            }

            Integer proximoId = this.getProximoId(con);
            empresa.setId_empresa(proximoId);

            String sql = "INSERT INTO EMPRESA (ID_EMPRESA, NOMEFANTASIA, CNPJ, RAZAOSOCIAL, INSCRICAOESTADUAL, SETOR)\n" +
                    "VALUES\n" +
                    "(?, ?, ?, ?, ?, ?);";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, empresa.getIdEmpresa());
            stmt.setString(2, empresa.getNomeFantasia());
            stmt.setString(3, empresa.getCnpj());
            stmt.setString(4, empresa.getRazaoSocial());
            stmt.setString(5, empresa.getInscricaoEstadual());
            stmt.setString(6, empresa.getSetor());

            int res = stmt.executeUpdate();
            if(res > 0) {
                System.out.println("Empresa adicionada");
            }
            else {
                System.out.println("Ocorreu um erro ao adicionar");
            }
            return empresa;
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "DELETE FROM EMPRESA WHERE id_empresa = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            if(res > 0) {
                System.out.println("Empresa removida com sucesso");
                return true;
            }
            System.out.println("Ocorreu um erro ao remover");
            return false;
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
    public boolean editar(Integer id, Empresa empresaAtualizada) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE EMPRESA SET ");
            sql.append(" NOMEFANTASIA = ?,");
            sql.append(" CNPJ = ?,");
            sql.append(" RAZAOSOCIAL = ? ");
            sql.append(" INSCRICAOESTADUAL = ? ");
            sql.append(" SETOR = ? ");
            sql.append(" WHERE ID_EMPRESA = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            stmt.setString(1, empresaAtualizada.getNomeFantasia());
            stmt.setString(2, empresaAtualizada.getCnpj());
            stmt.setString(3, empresaAtualizada.getRazaoSocial());
            stmt.setString(4, empresaAtualizada.getInscricaoEstadual());
            stmt.setString(5, empresaAtualizada.getSetor());
            stmt.setInt(6, empresaAtualizada.getIdEmpresa());

            int res = stmt.executeUpdate();
            if(res > 0) {
                System.out.println("Empresa atualizada com sucesso");
                return true;
            }
            System.out.println("Ocorreu um erro ao atualizar");
            return false;
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
    public List<Empresa> listar() throws BancoDeDadosException {
        List<Empresa> empresas = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM EMPRESA";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Empresa empresa = new Empresa();
                empresa.setId_empresa(res.getInt("ID_EMPRESA"));
                empresa.setNomeFantasia(res.getString("NOMEFANTASIA"));
                empresa.setCnpj(res.getString("CNPJ"));
                empresa.setRazaoSocial(res.getString("RAZAOSOCIAL"));
                empresa.setInscricaoEstadual(res.getString("INSCRICAOESTADUAL"));
                empresa.setSetor(res.getString("SETOR"));
                empresa.setIdUsuario(res.getInt("USUARIO_ID"));
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
<<<<<<< HEAD
}
=======

    public Empresa buscaPorId(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "SELECT * FROM EMPRESA WHERE id_empresa = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery(sql);
            if (res.next()) {
                Empresa empresa = new Empresa();
                empresa.setId_empresa(res.getInt("ID_EMPRESA"));
                empresa.setNomeFantasia(res.getString("NOMEFANTASIA"));
                empresa.setCnpj(res.getString("CNPJ"));
                empresa.setRazaoSocial(res.getString("RAZAOSOCIAL"));
                empresa.setInscricaoEstadual(res.getString("INSCRICAOESTADUAL"));
                empresa.setSetor(res.getString("SETOR"));
                empresa.setIdUsuario(res.getInt("USUARIO_ID"));
                return empresa;
            }
        throw new EmpresaNaoEncontradaException();

        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } catch (EmpresaNaoEncontradaException e) {
            throw new RuntimeException(e.getMessage());
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

>>>>>>> develop
