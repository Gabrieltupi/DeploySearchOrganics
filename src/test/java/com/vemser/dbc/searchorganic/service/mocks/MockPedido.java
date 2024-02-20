package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.dto.pedido.validacoes.IValidarPedido;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoResponsePedidoDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.model.pk.ProdutoXPedidoPK;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.validadores.TipoEntrega;
import org.springframework.security.core.parameters.P;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockPedido {
    public static Pedido retornaPedidoEntity(){
        Pedido pedido= new Pedido();
        pedido.setIdPedido(1);
        Usuario usuario= MockUsuario.retornarUsuario();
        pedido.setUsuario(usuario);

        Endereco endereco=MockEndereco.retornarEndereco();
        pedido.setEndereco(endereco);

        Empresa empresa= MockEmpresa.retornarEmpresa();
        pedido.setEmpresa(empresa);

        pedido.setCupom(new Cupom(1,"cupom10", TipoAtivo.S,"cupom 10 de desconto", BigDecimal.valueOf(10),empresa.getIdEmpresa()));

        pedido.setFormaPagamento(FormaPagamento.CREDITO);
        pedido.setStatusPedido(StatusPedido.A_CAMINHO);
        pedido.setDataDePedido(LocalDate.of(2024,02,19));
        pedido.setDataEntrega(LocalDate.of(2024,02,22));
        pedido.setPrecoFrete(BigDecimal.valueOf(12.0));
        pedido.setPrecoCarrinho(BigDecimal.valueOf(32.0));
        pedido.setCodigoDeRastreio("1V5387ES1UP8");
        return pedido;
    }

    public static PedidoRastreioDTO retornaPedidoRastreioDto(){
        PedidoRastreioDTO pedidoRastreioDTO= new PedidoRastreioDTO(retornaPedidoDto().getIdPedido(), retornaPedidoDto().getCodigoDeRastreio(),retornaPedidoDto().getStatusPedido());
        return pedidoRastreioDTO;
    }
    public static List<Pedido> retornaListaPedidoEntity(){
        return List.of(retornaPedidoEntity(), retornaPedidoEntity());
    }
    public static List<Integer> retornaListaPedidoId(){
        return List.of(retornaPedidoEntity().getIdPedido(), retornaPedidoEntity().getIdPedido());
    }
    public static List<PedidoDTO> retornaListaPedidoDTO(){
        return List.of(retornaPedidoDto(), retornaPedidoDto());
    }


    public static PedidoXProduto retornaPedidoXProdutoEntity(){
        Pedido pedido= retornaPedidoEntity();
        Produto produto= MockProduto.retornarProdutoEntity();

        PedidoXProduto pedidoXProduto = new PedidoXProduto();
        ProdutoXPedidoPK pk = new ProdutoXPedidoPK();
        pk.setIdProduto(produto.getIdProduto());
        pk.setIdPedido(pedido.getIdPedido());

        pedidoXProduto.setProdutoXPedidoPK(pk);
        pedidoXProduto.setPedido(pedido);
        pedidoXProduto.setProduto(produto);
        pedidoXProduto.setQuantidade(new Random().nextInt(10));

        return pedidoXProduto;
    }

    public static  List<PedidoXProduto> retornaListaPedidoXPROduto(){
        return List.of(retornaPedidoXProdutoEntity(), retornaPedidoXProdutoEntity());
    }

    public static List<ProdutoPedidoDTO> retornarListaProdutoPedidoDto() {
        ProdutoResponsePedidoDTO produtoResponsePedidoDTO = MockProduto.retornarProdutoResponsePedidoDTO();
        List<ProdutoPedidoDTO> lista = new ArrayList<>();

        ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO();
        produtoPedidoDTO.setProduto(produtoResponsePedidoDTO);
        produtoPedidoDTO.setQuantidade(1);
        lista.add(produtoPedidoDTO);

        return lista;
    }
    public static ProdutoPedidoDTO retornarProdutoPedidoDto() {
        ProdutoResponsePedidoDTO produtoResponsePedidoDTO = MockProduto.retornarProdutoResponsePedidoDTO();
        ProdutoPedidoDTO lista = new ProdutoPedidoDTO();

        ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO();
        produtoPedidoDTO.setProduto(produtoResponsePedidoDTO);
        produtoPedidoDTO.setQuantidade(1);


        return lista;
    }

    public static PedidoDTO retornaPedidoDto(){
        Pedido pedido = retornaPedidoEntity();
        PedidoDTO pedidoDto = retornaPedidoDTOPorEntity(pedido, retornaListaProdutoXPedido(pedido, MockProduto.retornarProdutoEntity()));

        return pedidoDto;
    }




    public static ArrayList<ProdutoCarrinhoCreate> retornaProdutoCarrinhoCreate(){
        ArrayList<ProdutoCarrinhoCreate> produtoCarrinhoCreates= new ArrayList<>();

        ProdutoCarrinhoCreate produtoCarrinho= new ProdutoCarrinhoCreate();
        produtoCarrinho.setIdProduto(MockProduto.retornarProdutoEntity().getIdProduto());
        produtoCarrinho.setQuantidade(15);

        produtoCarrinhoCreates.add(produtoCarrinho);
        return produtoCarrinhoCreates;
    }

    public static PedidoCreateDTO retornarPedidoCreateDto(){
        PedidoCreateDTO pedidoCreateDto= new PedidoCreateDTO();
        pedidoCreateDto.setIdEndereco(MockEndereco.retornarEndereco().getIdEndereco());

        Empresa empresa= MockEmpresa.retornarEmpresa();
        pedidoCreateDto.setIdEmpresa(MockEmpresa.retornarEmpresa().getIdEmpresa());
        pedidoCreateDto.setIdCupom(retornaPedidoEntity().getCupom().getIdCupom());
        pedidoCreateDto.setFormaPagamento(FormaPagamento.CREDITO);
        pedidoCreateDto.setDataDePedido(LocalDate.of(2024,02,19));
        pedidoCreateDto.setDataEntrega(LocalDate.of(2024,02,22));
        pedidoCreateDto.setPrecoFrete(BigDecimal.valueOf(12.0));
        pedidoCreateDto.setProdutosCarrinho(retornaProdutoCarrinhoCreate());
        return pedidoCreateDto;
    }

    public static PedidoUpdateDTO retornarPedidoUpdateDto(){
        PedidoUpdateDTO pedidoUpdateDto= new PedidoUpdateDTO();
        pedidoUpdateDto.setIdEndereco(MockEndereco.retornarEndereco().getIdEndereco());
        pedidoUpdateDto.setStatusPedido(StatusPedido.ENTREGUE);
        pedidoUpdateDto.setFormaPagamento(FormaPagamento.DEBITO);
        pedidoUpdateDto.setTipoEntrega(TipoEntrega.ENTREGA);
        pedidoUpdateDto.setDataEntrega(LocalDate.of(2024,02,22));
        pedidoUpdateDto.setPrecoFrete(BigDecimal.valueOf(12.0));
        return pedidoUpdateDto;
    }

    public static PedidoXProduto retornaPedidoXProduto(Pedido pedido, Produto produto) {
        PedidoXProduto pedidoXProduto = new PedidoXProduto();
        ProdutoXPedidoPK pk = new ProdutoXPedidoPK();
        pk.setIdProduto(produto.getIdProduto());
        pk.setIdPedido(pedido.getIdPedido());

        pedidoXProduto.setProdutoXPedidoPK(pk);
        pedidoXProduto.setPedido(pedido);
        pedidoXProduto.setProduto(produto);
        pedidoXProduto.setQuantidade(new Random().nextInt(10));

        return pedidoXProduto;
    }
    public static List<PedidoXProduto> retornaListaProdutoXPedido(Pedido pedido, Produto produto){
        return List.of(retornaPedidoXProduto(pedido, produto),retornaPedidoXProduto(pedido, produto));
    }
    public static PedidoDTO retornaPedidoDTOPorEntity(Pedido pedido,   List<PedidoXProduto> pedProdEntity){
        UsuarioDTO usuarioDTO = MockUsuario.retornarUsuarioDTO(pedido.getUsuario());
        EnderecoDTO enderecoDTO = MockEndereco.retornarEnderecoDTO(pedido.getEndereco());
        EmpresaDTO empresaDTO = MockEmpresa.retornarEmpresaDTO(pedido.getEmpresa());
        CupomDTO cupomDTO = MockCupom.retornarCupomDTO(pedido.getCupom());
        List<ProdutoPedidoDTO> produtos = retornarProdutosPedidoDTO(pedProdEntity);

        PedidoDTO pedidoDTO = new PedidoDTO(pedido, usuarioDTO, enderecoDTO, cupomDTO, produtos, empresaDTO);

        return pedidoDTO;
    }

    private static List<ProdutoPedidoDTO> retornarProdutosPedidoDTO(List<PedidoXProduto> listaPedidoXPrd) {
        List<ProdutoPedidoDTO> produtosPedidoDTO = new ArrayList<>();
        for(PedidoXProduto pedidoXProduto : listaPedidoXPrd){
            ProdutoPedidoDTO pedidoXprodDTO = new ProdutoPedidoDTO();
            ProdutoResponsePedidoDTO produtoResponsePedidoDTO = new ProdutoResponsePedidoDTO(pedidoXProduto.getProduto());

            pedidoXprodDTO.setQuantidade(pedidoXProduto.getQuantidade());
            pedidoXprodDTO.setProduto(produtoResponsePedidoDTO);

            produtosPedidoDTO.add(pedidoXprodDTO);

        }

        return produtosPedidoDTO;
    }
    public static PedidoRastreioDTO retornarPedidoRastreioOk(Pedido pedido, String codRastreio){
        PedidoRastreioDTO pedidoRastreioDTO = new PedidoRastreioDTO();
        pedidoRastreioDTO.setCodigoDeRastreio(codRastreio);
        pedidoRastreioDTO.setStatusPedido(StatusPedido.A_CAMINHO);
        pedidoRastreioDTO.setIdPedido(pedido.getIdPedido());
        return pedidoRastreioDTO;
    }
}
