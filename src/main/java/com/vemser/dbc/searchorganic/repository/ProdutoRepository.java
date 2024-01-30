package com.vemser.dbc.searchorganic.repository;

import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class ProdutoRepository implements IRepositoryJDBC<Integer, Produto> {
    private final ConexaoBancoDeDados conexaoBancoDeDados;

    @Override
    public Integer getProximoId(Connection con) throws SQLException {
        String sql = "SELECT SEQ_PRODUTO.nextval mysequence from DUAL";

        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Produto adicionar(Produto produto) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            Integer proximoId = this.getProximoId(con);
            produto.setIdProduto(proximoId);

            String sql = "INSERT INTO PRODUTO\n" +
                    "(ID_PRODUTO, ID_EMPRESA, NOME, DESCRICAO, PRECO, QUANTIDADE_DISPONIVEL, TIPO_CATEGORIA, TAXA, UNIDADE_MEDIDA, URL_IMAGEM)\n" +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, produto.getIdProduto());
            stmt.setInt(2, produto.getIdEmpresa());
            stmt.setString(3, produto.getNome());
            stmt.setString(4, produto.getDescricao());
            stmt.setBigDecimal(5, produto.getPreco());
            stmt.setBigDecimal(6, produto.getQuantidade());
            stmt.setInt(7, produto.getCategoria().getValor());
            stmt.setDouble(8, produto.getTaxa());
            stmt.setString(9, produto.getUnidadeMedida().toString());
            stmt.setString(10, produto.getUrlImagem());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("produto adicionada");
            } else {
                System.out.println("Ocorreu um erro ao adicionar");
            }

            System.out.println("aqui");
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
    public Boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "UPDATE PRODUTO SET ATIVO='N' WHERE id_produto = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int res = stmt.executeUpdate();
                if (res > 0) {
                    System.out.println("Produto removido com sucesso");
                    return true;
                } else {
                    System.out.println("Não foi possível remover o produto. Produto não encontrado.");
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
    public Produto editar(Integer id, Produto produto) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();

            String sql = "UPDATE PRODUTO SET " +
                    " nome = ?," +
                    " descricao = ?," +
                    " preco = ?, " +
                    " quantidade_disponivel = ?," +
                    " tipo_categoria = ?," +
                    " taxa = ?, " +
                    " unidade_medida = ?, " +
                    " url_imagem = ? " +
                    " WHERE id_produto = ? ";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setBigDecimal(4, produto.getQuantidade());
            stmt.setInt(5, produto.getCategoria().getValor());
            stmt.setDouble(6, produto.getTaxa());
            stmt.setString(7, produto.getUnidadeMedida().toString());
            stmt.setString(8, produto.getUrlImagem());
            stmt.setInt(9, produto.getIdProduto());

            int res = stmt.executeUpdate();
            if (res > 0) {
                System.out.println("produto adicionada");
            } else {
                System.out.println("Ocorreu um erro ao adicionar");
            }

            System.out.println("aqui");
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
    public List<Produto> listar() throws BancoDeDadosException {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
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
                produto.setUrlImagem(res.getString("url_imagem"));
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

    public Produto buscarProdutoPorId(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
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
                produto.setUrlImagem(res.getString("url_imagem"));
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

    public List<Produto> listarProdutosPorCategoria(Integer categoria) throws BancoDeDadosException {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
            String sql = "SELECT * FROM PRODUTO WHERE TIPO_CATEGORIA = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, categoria);

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
                produto.setUrlImagem(res.getString("url_imagem"));
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

    public List<Produto> listarProdutosLoja(Integer idLoja) throws BancoDeDadosException {
        List<Produto> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = conexaoBancoDeDados.getConnection();
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
                produto.setUrlImagem(res.getString("url_imagem"));
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

    public void atualizarEstoque(Integer idProduto, Integer quantidade) {

    }

    public void save(Produto produto) {
    }
}
