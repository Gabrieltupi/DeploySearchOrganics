package com.vemser.dbc.searchorganic.repository;


import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
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
            pedido.setId(proximoId);
            String sql = "INSERT INTO PEDIDO (ID_PEDIDO, ID_USUARIO, ID_ENDERECO, ID_CUPOM, FORMA_PAGAMENTO, STATUS_PEDIDO, " +
                    "DATA_DE_PEDIDO, DATA_ENTREGA, PRECO_CARRINHO, PRECO_FRETE) " +
                    "VALUES\n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, pedido.getId());
            stmt.setInt(2, pedido.getUsuarioId());
            stmt.setInt(3, pedido.getEndereco().getId());
            if(pedido.getCupom().getCupomId() != null ){
                stmt.setInt(4, pedido.getCupom().getCupomId());
            }
            else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setString(5, pedido.getFormaPagamento().toString());
            stmt.setString(6,"AGUARDANDO_PAGAMENTO");
            stmt.setDate(7, Date.valueOf(pedido.getInicioEntrega()));
            stmt.setDate(8, Date.valueOf(pedido.getDataDeEntrega()));
            stmt.setBigDecimal(9, pedido.getPrecoCarrinho());
            stmt.setBigDecimal(10, pedido.getValorFrete());

            int res = stmt.executeUpdate();
            if(res > 0) {
                for(ProdutoCarrinho produto : pedido.getProdutos()){ // TODO: implementar logica
                    String query = "INSERT INTO PEDIDOXPRODUTO (ID_PEDIDO, ID_PRODUTO, QUANTIDADE) VALUES (?, ?, ?)";
                    PreparedStatement stt = con.prepareStatement(query);
                    stt.setInt(1, pedido.getId());
                    stt.setInt(2, produto.getProduto().getIdProduto());
                    stt.setBigDecimal(3, produto.getQuantidadePedida());
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
    public boolean remover(Integer id) throws BancoDeDadosException {
        Connection con = null;
        try {
            con = ConexaoBancoDeDados.getConnection();

            String sql = "UPDATE PEDIDO SET STATUS_PEDIDO = 'CANCELADO' WHERE ID_PEDIDO = ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate();

            String query = "SELECT P.ID_PRODUTO, PEXP.QUANTIDADE, P.QUANTIDADE_DISPONIVEL FROM PRODUTO P " +
                    "INNER JOIN PEDIDOXPRODUTO PEXP ON PEXP.ID_PRODUTO = P.ID_PRODUTO " +
                    "INNER JOIN PEDIDO PED ON PED.ID_PEDIDO = PEXP.ID_PEDIDO";
            ResultSet rst = stmt.executeQuery(query);

            while (rst.next()) {
                    int idProduto = rst.getInt("ID_PRODUTO");
                    BigDecimal quantidadeDisponivel= rst.getBigDecimal("QUANTIDADE_DISPONIVEL");
                    BigDecimal quantidade = rst.getBigDecimal("QUANTIDADE");
                    quantidadeDisponivel.add(quantidade);
                    String novaQuery = "UPDATE PRODUTO SET QUANTIDADE_DISPONIVEL = ? WHERE ID_PRODUTO = ?";
                    PreparedStatement sttq = con.prepareStatement(novaQuery);
                    sttq.setBigDecimal(1, quantidadeDisponivel);
                    sttq.setInt(2, idProduto);
                    sttq.execute();
            }

            if(res > 0) {
                System.out.println("Pedido cancelado");
                return true;
            }
            System.out.println("Ocorreu um erro ao cancelar");
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
    public boolean editar(Integer id, Pedido pedido) throws BancoDeDadosException {
        return false;
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
                pedido.setId(res.getInt("ID_PEDIDO"));
                pedido.setUsuarioId(res.getInt("ID_USUARIO"));
                pedido.setEndereco(new EnderecoRepository().buscarPorId(res.getInt("ID_ENDERECO")));
                pedido.setCupom(new CupomRepository().buscarPorId(res.getInt("ID_CUPOM")));
                pedido.setFormaPagamento(FormaPagamento.valueOf(res.getString("FORMA_PAGAMENTO")));
                pedido.setStatusPedido(StatusPedido.valueOf(res.getString("STATUS_PEDIDO")));
                pedido.setValorFrete(res.getBigDecimal("PRECO_FRETE"));
                pedido.setPrecoCarrinho(res.getBigDecimal("PRECO_CARRINHO"));
                pedido.setDataDeEntrega(res.getDate("DATA_ENTREGA").toLocalDate());
                pedido.setInicioEntrega(res.getDate("DATA_DE_PEDIDO").toLocalDate());
                pedido.setTotal(pedido.getPrecoCarrinho().add(pedido.getValorFrete()));
                pedido.setProdutos(this.listarProdutosDoPedido(pedido.getId()));
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
                BigDecimal quantidadePedida = res.getBigDecimal("QUANTIDADE");
                ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho(produto, quantidadePedida);
                produtos.add(produtoCarrinho);
            }
        } catch (SQLException e) {
            throw new BancoDeDadosException(e.getCause());
        } finally {
            ConexaoBancoDeDados.closeConnection(con);
        }
        return produtos;
    }
}
