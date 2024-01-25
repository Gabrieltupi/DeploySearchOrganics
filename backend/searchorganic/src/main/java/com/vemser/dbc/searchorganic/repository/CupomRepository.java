package com.vemser.dbc.searchorganic.repository;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Cupom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CupomRepository implements IRepositoryJDBC<Integer, Cupom> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;
    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        try {
            String sql = "SELECT SEQ_CUPOM.nextval mysequence from DUAL";
            Statement stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if (res.next()) {
                return res.getInt("mysequence");
            }

            return null;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
    }

    @Override
    public Cupom adicionar(Cupom cupom) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            cupom.setCupomId(proximoId);

            String sql = "INSERT INTO CUPOM\n" +
                    "(id_cupom, id_empresa, nome_cupom, ativo, descricao, taxa_desconto)\n" +
                    "VALUES(?, ?, ?, ?, ?, :)\n";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, cupom.getCupomId());
            stmt.setInt(2, cupom.getIdEmpresa());
            stmt.setString(2, cupom.getNomeCupom());
            stmt.setString(3, cupom.isAtivo());
            stmt.setString(4, cupom.getDescricao());
            stmt.setBigDecimal(5, cupom.getTaxaDeDesconto());

            int res = stmt.executeUpdate();
            System.out.println("adicionarCupom.res=" + res);
            return cupom;

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

            String sql = "UPDATE CUPOM SET ativo = 'N' WHERE id_cupom = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int res = stmt.executeUpdate();

                if (res > 0) {
                    System.out.println("Cupom removido com sucesso");
                    return true;
                } else {
                    System.out.println("Cupom não encontrado ou já inativo");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e);
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
    public Boolean editar(Integer id, Cupom cupom) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE CUPOM SET \n");

            if (cupom.getNomeCupom() != null) {
                sql.append(" nome_cupom = ?,");
            }
            if (cupom.getAtivo() != null) {
                sql.append(" ativo = ?,");
            }
            if (cupom.getDescricao() != null) {
                sql.append(" descricao = ?,");
            }
            if (cupom.getTaxaDeDesconto() != null) {
                sql.append(" taxa_desconto = ?, ");
            }
            if (cupom.getIdEmpresa() != null) {
                sql.append(" id_empresa = ? ");
            }

            sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id_cupom = ? ");

            PreparedStatement stmt = con.prepareStatement(sql.toString());

            if (cupom.getNomeCupom() != null) {
                stmt.setString(1, cupom.getNomeCupom());
            }
            if (cupom.getAtivo() != null) {
                stmt.setString(2, cupom.getAtivo().getStatus());
            }
            if (cupom.getDescricao() != null) {
                stmt.setString(3, cupom.getDescricao());
            }
            if (cupom.getTaxaDeDesconto() != null) {
                stmt.setBigDecimal(4, cupom.getTaxaDeDesconto());
            }
            if (cupom.getIdEmpresa() != null) {
                stmt.setInt(5, cupom.getIdEmpresa());
            }

            stmt.setInt(6, id);

            int res = stmt.executeUpdate();
            System.out.println("editarCupom.res=" + res);

            return res > 0;
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
    public List<Cupom> listar() throws BancoDeDadosException {
        List<Cupom> cupoms = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CUPOM";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Cupom cupom = new Cupom();
                cupom.setCupomId(res.getInt("ID_CUPOM"));
                cupom.setNomeCupom(res.getString("NOME_CUPOM"));
                cupom.setAtivo(res.getString("ATIVO"));
                cupom.setDescricao(res.getString("DESCRICAO"));
                cupom.setTaxaDeDesconto(res.getBigDecimal("TAXA_DESCONTO"));
                cupom.setIdEmpresa(res.getInt("ID_EMPRESA"));
                cupoms.add(cupom);
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
        return cupoms;
    }

    public Cupom buscarPorId(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM CUPOM WHERE ID_CUPOM = ?";

            PreparedStatement stt = con.prepareStatement(sql);
            stt.setInt(1, id);

            ResultSet res = stt.executeQuery();

            if (res.next()) {
                Cupom cupom = new Cupom();
                cupom.setCupomId(res.getInt("ID_CUPOM"));
                cupom.setNomeCupom(res.getString("NOME_CUPOM"));
                cupom.setAtivo(res.getString("ATIVO"));
                cupom.setDescricao(res.getString("DESCRICAO"));
                cupom.setTaxaDeDesconto(res.getBigDecimal("TAXA_DESCONTO"));
                cupom.setIdEmpresa(res.getInt("ID_EMPRESA"));
                return cupom;
            }
            return null;
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

    public List<Cupom> listarCupomPorEmpresa(int idEmpresa) throws BancoDeDadosException {
        List<Cupom> cupoms = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM CUPOM WHERE ID_EMPRESA = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, idEmpresa);
                ResultSet res = stmt.executeQuery();

                while (res.next()) {
                    Cupom cupom = new Cupom();
                    cupom.setCupomId(res.getInt("ID_CUPOM"));
                    cupom.setNomeCupom(res.getString("NOME_CUPOM"));
                    cupom.setAtivo(res.getString("ATIVO"));
                    cupom.setDescricao(res.getString("DESCRICAO"));
                    cupom.setTaxaDeDesconto(res.getBigDecimal("TAXA_DESCONTO"));
                    cupom.setIdEmpresa(res.getInt("ID_EMPRESA"));
                    cupoms.add(cupom);
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
        return cupoms;
    }

    public Cupom getCupomByNameAndEmpresa(String nomeCupom, int idEmpresa) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM CUPOM WHERE NOME_CUPOM = ? AND ID_EMPRESA = ?";
            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setString(1, nomeCupom);
                stmt.setInt(2, idEmpresa);
                ResultSet res = stmt.executeQuery();

                if (res.next()) {
                    Cupom cupom = new Cupom();
                    cupom.setCupomId(res.getInt("ID_CUPOM"));
                    cupom.setNomeCupom(res.getString("NOME_CUPOM"));
                    cupom.setAtivo(res.getString("ATIVO"));
                    cupom.setDescricao(res.getString("DESCRICAO"));
                    cupom.setTaxaDeDesconto(res.getBigDecimal("TAXA_DESCONTO"));
                    cupom.setIdEmpresa(res.getInt("ID_EMPRESA"));
                    return cupom;
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
        return null;
    }



}
