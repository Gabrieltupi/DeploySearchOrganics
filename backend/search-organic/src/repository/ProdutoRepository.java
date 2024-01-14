package repository;

import exceptions.BancoDeDadosException;
import modelo.Produto;
import utils.TipoCategoria;
import utils.UnidadeMedida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository implements Repository<Integer, Produto> {

    @Override
    public Integer getProximoId(Connection con) throws SQLException {
        String sql= "SELECT SEQ_PRODUTO.nextval mysequence from DUAL";

        Statement stmt= con.createStatement();
        ResultSet res= stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Produto adicionar(Produto produto) throws BancoDeDadosException {
        Connection con= null;
        try{
            con=ConexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            produto.setIdProduto(proximoId);

            String sql = "INSERT INTO PRODUTO\n" +
                    "(iD_PRODUTO, ID_EMPRESA, NOME, DESCRICAO, PRECO, QUANTIDADE_DISPONIVEL,TIPO_CATEGORIA, TAXA, UNIDADE_MEDIDA)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n";

            PreparedStatement stmt = con.prepareStatement(sql);
                stmt.setInt(1,produto.getIdProduto());
                stmt.setInt(2,produto.getIdEmpresa());
                stmt.setString(3,produto.getNome());
                stmt.setString(4,produto.getDescricao());
                stmt.setBigDecimal(5,produto.getPreco());
                stmt.setBigDecimal(6,produto.getQuantidade());
                stmt.setString(7, produto.getCategoria());
                stmt.setDouble(8,produto.getTaxa());
                stmt.setString(9, produto.getUnidadeMedida().toString());
            int res = stmt.executeUpdate();
            if(res > 0) {
                System.out.println("produto adicionada");
            }
            else {
                System.out.println("Ocorreu um erro ao adicionar");
            }

            return produto;
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
            String sql = "DELETE FROM PRODUTO WHERE id_produto = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            int res = stmt.executeUpdate();
            if(res > 0) {
                System.out.println("Produto removida com sucesso");
                return true;
            }
            System.out.println("Não foi possivel remover");

        }catch(SQLException e) {
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

        return false;
    }

    @Override
    public boolean editar(Integer id, Produto produto) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            StringBuilder sql = new StringBuilder();
            sql.append("UPDATE PRODUTO SET ");
            sql.append(" nome = ?,");
            sql.append(" descricao = ?,");
            sql.append(" preco = ? ");
            sql.append(" quantidade_disponivel = ?,");
            sql.append(" tipo_categoria = ?,");
            sql.append(" taxa = ? ");
            sql.append(" unidade_medida = ? ");
            sql.append(" WHERE id_produto = ? ");

            PreparedStatement stmt=con.prepareStatement(sql.toString());

            stmt.setString(1,produto.getNome());
            stmt.setString(2,produto.getDescricao());
            stmt.setBigDecimal(3,produto.getPreco());
            stmt.setBigDecimal(4,produto.getQuantidade());
            stmt.setString(5, produto.getCategoria());
            stmt.setDouble(5,produto.getTaxa());
            stmt.setString(6, produto.getUnidadeMedida().toString());
            stmt.setInt(7,produto.getIdProduto());
            int res = stmt.executeUpdate();

            if(res > 0) {
                System.out.println("Produto atualizada com sucesso");
                return true;
            }
            System.out.println("Ocorreu um erro ao atualizar");
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
        return false;
    }

    @Override
    public List<Produto> listar() throws BancoDeDadosException {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM PRODUTO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(res.getInt("id_produto"));
                produto.setIdEmpresa(res.getInt("id_empresa"));
                produto.setNome(res.getString("nome"));
                produto.setDescricao(res.getString("descricao"));
                produto.setPreco(res.getBigDecimal("preco"));
                produto.setQuantidade(res.getBigDecimal("quantidade_disponivel"));
                produto.setCategoria(TipoCategoria.fromInt(res.getInt("tipo_categoria")));
                produto.setTaxa(res.getDouble("taxa"));
                produto.setUnidadeMedida(UnidadeMedida.fromString(res.getString("unidade_medida")));
                produtos.add(produto);
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
        return produtos;
    }

    public Produto buscarProdutoPorId(int id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM PRODUTO WHERE ID_PRODUTO = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(res.getInt("id_produto"));
                produto.setIdEmpresa(res.getInt("id_empresa"));
                produto.setNome(res.getString("nome"));
                produto.setDescricao(res.getString("descricao"));
                produto.setPreco(res.getBigDecimal("preco"));
                produto.setQuantidade(res.getBigDecimal("quantidade_disponivel"));
                produto.setCategoria(TipoCategoria.fromInt(res.getInt("tipo_categoria")));
                produto.setTaxa(res.getDouble("taxa"));
                produto.setUnidadeMedida(UnidadeMedida.fromString(res.getString("unidade_medida")));
                return produto;
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

    public List<Produto> listarProdutosPorCategoria(TipoCategoria categoria) throws BancoDeDadosException {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM PRODUTO WHERE TIPO_CATEGORIA = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, categoria.name());

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(res.getInt("id_produto"));
                produto.setIdEmpresa(res.getInt("id_empresa"));
                produto.setNome(res.getString("nome"));
                produto.setDescricao(res.getString("descricao"));
                produto.setPreco(res.getBigDecimal("preco"));
                produto.setQuantidade(res.getBigDecimal("quantidade_disponivel"));
                produto.setCategoria(TipoCategoria.fromInt(res.getInt("tipo_categoria")));
                produto.setTaxa(res.getDouble("taxa"));
                produto.setUnidadeMedida(UnidadeMedida.fromString(res.getString("unidade_medida")));
                produtos.add(produto);
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
        return produtos;
    }
    public List<Produto> listarProdutosLoja(int idLoja) throws BancoDeDadosException {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM PRODUTO WHERE ID_EMPRESA = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idLoja);

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(res.getInt("id_produto"));
                produto.setIdEmpresa(res.getInt("id_empresa"));
                produto.setNome(res.getString("nome"));
                produto.setDescricao(res.getString("descricao"));
                produto.setPreco(res.getBigDecimal("preco"));
                produto.setQuantidade(res.getBigDecimal("quantidade_disponivel"));
                produto.setCategoria(TipoCategoria.fromInt(res.getInt("tipo_categoria")));
                produto.setTaxa(res.getDouble("taxa"));
                produto.setUnidadeMedida(UnidadeMedida.fromString(res.getString("unidade_medida")));
                produtos.add(produto);
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
        return produtos;
    }
}
