package com.vemser.dbc.searchorganic.repository;


import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Pedido;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.model.ProdutoCarrinho;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PedidoRepository implements IRepositoryJDBC<Integer, Pedido> {

    @Override
    public Integer getProximoId(Connection connection) throws SQLException {
        String sql = "SELECT SEQ_PEDIDO.nextval mysequence from DUAL";

        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        if (res.next()) {
            return res.getInt("mysequence");
        }

        return null;
    }

    @Override
    public Pedido adicionar(Pedido pedido) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Integer proximoId = this.getProximoId(con);

            pedido.setIdPedido(proximoId);
            pedido.setStatusPedido(StatusPedido.AGUARDANDO_PAGAMENTO);
            String sql = "INSERT INTO PEDIDO (ID_PEDIDO, ID_USUARIO, ID_ENDERECO, ID_CUPOM, FORMA_PAGAMENTO, STATUS_PEDIDO, " +
                    "DATA_DE_PEDIDO, DATA_ENTREGA, PRECO_CARRINHO, PRECO_FRETE) " +
                    "VALUES\n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, pedido.getIdPedido());
            stmt.setInt(2, pedido.getIdUsuario());
            stmt.setInt(3, pedido.getIdEndereco());
            if(pedido.getIdCupom() != null ){
                stmt.setInt(4, pedido.getIdCupom());
            }
            else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setString(5, pedido.getFormaPagamento().toString());
            stmt.setString(6,pedido.getStatusPedido().toString());
            stmt.setDate(7, Date.valueOf(pedido.getDataDePedido()));
            stmt.setDate(8, Date.valueOf(pedido.getDataEntrega()));
            stmt.setBigDecimal(9, pedido.getPrecoCarrinho());
            stmt.setBigDecimal(10, pedido.getPrecoFrete());

            int res = stmt.executeUpdate();
            if(res > 0) {
                for(ProdutoCarrinho produto : pedido.getProdutos()){ // TODO: implementar logica
                    String query = "INSERT INTO PEDIDOXPRODUTO (ID_PEDIDO, ID_PRODUTO, QUANTIDADE) VALUES (?, ?, ?)";
                    PreparedStatement stt = con.prepareStatement(query);
                    stt.setInt(1, pedido.getIdPedido());
                    stt.setInt(2, produto.getIdProduto());
                    stt.setInt(3, produto.getQuantidade());
                    stt.execute();
                }
                System.out.println("Pedido realizado");
            }
            else {
                System.out.println("Ocorreu um erro ao criar o pedido");
            }
            return pedido;
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
           ConexaoBancoDeDados.closeConnection(con);
        }
    }

    @Override
    public Boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE PEDIDO SET STATUS_PEDIDO = 'CANCELADO' WHERE ID_PEDIDO = ?";

            try (PreparedStatement stmt = con.prepareStatement(sql)) {
                stmt.setInt(1, id);

                int res = stmt.executeUpdate();

                String query = "SELECT P.ID_PRODUTO, PEXP.QUANTIDADE, P.QUANTIDADE_DISPONIVEL FROM PRODUTO P " +
                        "INNER JOIN PEDIDOXPRODUTO PEXP ON PEXP.ID_PRODUTO = P.ID_PRODUTO " +
                        "INNER JOIN PEDIDO PED ON PED.ID_PEDIDO = PEXP.ID_PEDIDO";
                try (ResultSet rst = stmt.executeQuery(query)) {
                    while (rst.next()) {
                        int idProduto = rst.getInt("ID_PRODUTO");
                        BigDecimal quantidadeDisponivel = rst.getBigDecimal("QUANTIDADE_DISPONIVEL");
                        BigDecimal quantidade = rst.getBigDecimal("QUANTIDADE");
                        quantidadeDisponivel = quantidadeDisponivel.add(quantidade);
                        String novaQuery = "UPDATE PRODUTO SET QUANTIDADE_DISPONIVEL = ? WHERE ID_PRODUTO = ?";
                        try (PreparedStatement sttq = con.prepareStatement(novaQuery)) {
                            sttq.setBigDecimal(1, quantidadeDisponivel);
                            sttq.setInt(2, idProduto);
                            sttq.executeUpdate();
                        }
                    }
                }

                if (res > 0) {
                    System.out.println("Pedido cancelado com sucesso");
                    return true;
                } else {
                    System.out.println("Ocorreu um erro ao cancelar o pedido");
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
    public boolean editar(Integer id, Pedido pedido) throws Exception {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            String sql = "UPDATE PEDIDO SET " +
                    "ID_ENDERECO = ?," +
                    "ID_CUPOM = ?," +
                    "FORMA_PAGAMENTO = ?," +
                    "STATUS_PEDIDO = ?," +
                    "DATA_ENTREGA = ?," +
                    "PRECO_CARRINHO = ?," +
                    "PRECO_FRETE = ? " +
                    "WHERE ID_PEDIDO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, pedido.getIdEndereco());
            if (pedido.getIdCupom() != null) {
                stmt.setInt(2, pedido.getIdCupom());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }
            stmt.setString(3, pedido.getFormaPagamento().toString());
            stmt.setString(4, pedido.getStatusPedido().toString());
            stmt.setDate(5, Date.valueOf(pedido.getDataEntrega()));
            stmt.setBigDecimal(6, pedido.getPrecoCarrinho());
            stmt.setBigDecimal(7, pedido.getPrecoFrete());
            stmt.setInt(8, id);


            int res = stmt.executeUpdate();
          if(res > 0){
              return true;
          }
          throw new RegraDeNegocioException("Pedido não encontrado");
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        }
        finally {
            ConexaoBancoDeDados.closeConnection(con);
        }
    }

    public boolean editarStatusPedido(Integer id, StatusPedido statusPedido) throws BancoDeDadosException {
        Connection con = null;
        System.out.println("Status atualizado com sucesso!");
        try {
            con = ConexaoBancoDeDados.getConnection();
            String sql = "UPDATE PEDIDO SET STATUS_PEDIDO = ? WHERE ID_PEDIDO = ?";
            try (PreparedStatement pstd = con.prepareStatement(sql)) {
                pstd.setString(1, statusPedido.toString());
                pstd.setInt(2, id);

                int linhasAfetadas = pstd.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Status editado com sucesso!");
                    return true;
                } else {
                    System.out.println("Status não foi editado!");
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            ConexaoBancoDeDados.closeConnection(con);
        }
    }

    @Override
    public List<Pedido> listar() throws BancoDeDadosException {
        List<Pedido> pedidos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM PEDIDO";

            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(res.getInt("ID_PEDIDO"));
                pedido.setIdUsuario(res.getInt("ID_USUARIO"));
                pedido.setIdEndereco(res.getInt("ID_ENDERECO"));
                pedido.setIdCupom(res.getInt("ID_CUPOM"));
                pedido.setFormaPagamento(FormaPagamento.valueOf(res.getString("FORMA_PAGAMENTO")));
                pedido.setStatusPedido(StatusPedido.valueOf(res.getString("STATUS_PEDIDO")));
                pedido.setPrecoFrete(res.getBigDecimal("PRECO_FRETE"));
                pedido.setPrecoCarrinho(res.getBigDecimal("PRECO_CARRINHO"));
                pedido.setDataEntrega(res.getDate("DATA_ENTREGA").toLocalDate());
                pedido.setDataDePedido(res.getDate("DATA_DE_PEDIDO").toLocalDate());
                pedidos.add(pedido);

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
        return pedidos;
    }

    public ArrayList<ProdutoCarrinho> listarProdutosDoPedido(int idPedido) throws BancoDeDadosException {
        ArrayList<ProdutoCarrinho> produtos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT PROD.*, PEXP.QUANTIDADE FROM PRODUTO PROD " +
                    "INNER JOIN PEDIDOXPRODUTO  PEXP ON PROD.ID_PRODUTO = PEXP.ID_PRODUTO " +
                    "INNER JOIN PEDIDO PED ON PEXP.ID_PEDIDO = PED.ID_PEDIDO " +
                    "WHERE PED.ID_PEDIDO = ? ";

            PreparedStatement stt = con.prepareStatement(sql);
            stt.setInt(1, idPedido);

            ResultSet res = stt.executeQuery();

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
                Integer quantidadePedida = res.getInt("QUANTIDADE");
                ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho(produto.getIdProduto(), quantidadePedida, produto.getIdEmpresa(), produto);
                produtos.add(produtoCarrinho);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            ConexaoBancoDeDados.closeConnection(con);
        }
        return produtos;
    }

    public List<Pedido> obterPedidoPorIdUsuario(Integer idUsuario) throws BancoDeDadosException {
        List<Pedido> pedidos = new ArrayList<>();
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM PEDIDO WHERE ID_USUARIO = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idUsuario);

            ResultSet res = stm.executeQuery();

            while (res.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(res.getInt("ID_PEDIDO"));
                pedido.setIdUsuario(res.getInt("ID_USUARIO"));
                pedido.setIdEndereco(res.getInt("ID_ENDERECO"));
                pedido.setIdCupom(res.getInt("ID_CUPOM"));
                pedido.setFormaPagamento(FormaPagamento.valueOf(res.getString("FORMA_PAGAMENTO")));
                pedido.setStatusPedido(StatusPedido.valueOf(res.getString("STATUS_PEDIDO")));
                pedido.setPrecoFrete(res.getBigDecimal("PRECO_FRETE"));
                pedido.setPrecoCarrinho(res.getBigDecimal("PRECO_CARRINHO"));
                pedido.setDataEntrega(res.getDate("DATA_ENTREGA").toLocalDate());
                pedido.setDataDePedido(res.getDate("DATA_DE_PEDIDO").toLocalDate());
                pedido.setProdutos(this.listarProdutosDoPedido(pedido.getIdPedido()));
                pedidos.add(pedido);

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
        return pedidos;
    }

    public Pedido buscaPorId(Integer idPedido) throws Exception {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();
            Statement stmt = con.createStatement();

            String sql = "SELECT * FROM PEDIDO WHERE ID_PEDIDO = ?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setInt(1, idPedido);

            ResultSet res = stm.executeQuery();

            if (res.next()) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(res.getInt("ID_PEDIDO"));
                pedido.setIdUsuario(res.getInt("ID_USUARIO"));
                pedido.setIdEndereco(res.getInt("ID_ENDERECO"));
                pedido.setIdCupom(res.getInt("ID_CUPOM"));
                pedido.setFormaPagamento(FormaPagamento.valueOf(res.getString("FORMA_PAGAMENTO")));
                pedido.setStatusPedido(StatusPedido.valueOf(res.getString("STATUS_PEDIDO")));
                pedido.setPrecoFrete(res.getBigDecimal("PRECO_FRETE"));
                pedido.setPrecoCarrinho(res.getBigDecimal("PRECO_CARRINHO"));
                pedido.setDataEntrega(res.getDate("DATA_ENTREGA").toLocalDate());
                pedido.setDataDePedido(res.getDate("DATA_DE_PEDIDO").toLocalDate());
                return pedido;
            }
            throw new RegraDeNegocioException("Pedido não encontrado");
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
}
