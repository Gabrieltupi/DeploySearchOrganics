package repository;

import exceptions.BancoDeDadosException;
import modelo.Pedido;
import modelo.ProdutoCarrinho;
import modelo.Usuario;
import utils.FormaPagamento;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class PedidoRepository implements Repository<Integer, Pedido>{

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
                    "DATA_DE_PEDIDO, data_ENTREGA, PRECO_CARRINHO, PRECO_FRETE)\n" +
                    "VALUES\n" +
                    "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, pedido.getId());
            stmt.setInt(2, pedido.getUsuarioId());
            stmt.setInt(3, pedido.getEndereco().getId());
            if(pedido.getCupom().getCupomId() != null ){
                stmt.setInt(4, pedido.getCupom().getCupomId());
            }
            else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.setString(5, pedido.getFormaPagamento().toString());
            stmt.setString(6,"AGUARDANDO_PAGAMENTO");
            stmt.setDate(7, Date.valueOf(pedido.getInicioEntrega()));
            stmt.setDate(8, Date.valueOf(pedido.getDataDeEntrega()));
            stmt.setBigDecimal(9, pedido.getTotal());
            stmt.setBigDecimal(10, pedido.getValorFrete());

            int res = stmt.executeUpdate();
            if(res > 0) {
                for(ProdutoCarrinho produto : pedido.getProdutos()){
                    String query = "INSERT INTO PEDIDOXPRODUTO (ID_PEDIDO, ID_PRODUTO, QUANTIDADE) VALUES (?, ?, ?)";
                    PreparedStatement stt = con.prepareStatement(query);
                    stt.setInt(1, pedido.getId());
                    stt.setInt(2, produto.getProduto().getId_Produto());
                    stt.setBigDecimal(3, produto.getQuantidadePedida());
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

            String sql = "UPDATE  PEDIDO SET STATUS_PEDIDO = CANCELADO  id_pedido= ?";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, id);

            int res = stmt.executeUpdate(sql);

            String query = "SELECT ID_PRODUTO, PEXP.QUANTIDADE, P.QUANTIDADE_DISPONIVEL FROM PRODUTO P " +
                    "INNER JOIN PEDIDOXPRODUTO PEXP ON PEXP.ID_PRODUTO = P.ID_PRODUTO" +
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

        ;
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

    @Override
    public List<Pedido> listar() throws BancoDeDadosException {
        return null;
    }


}
