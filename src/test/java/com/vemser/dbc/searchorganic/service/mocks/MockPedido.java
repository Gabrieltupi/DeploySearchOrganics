package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.cupom.CupomDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.pedido.*;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoResponsePedidoDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.model.pk.ProdutoXPedidoPK;
import com.vemser.dbc.searchorganic.utils.FormaPagamento;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.validadores.TipoEntrega;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    public static List<Pedido> retornaListaPedidoEntity(){
        return List.of(retornaPedidoEntity(), retornaPedidoEntity());
    }
    public static List<Integer> retornaListaPedidoId(){
        return List.of(retornaPedidoEntity().getIdPedido(), retornaPedidoEntity().getIdPedido());
    }
    public static List<PedidoDTO> retornaListaPedidoDTO(){
        return List.of(retornaPedidoDto(), retornaPedidoDto());
    }

    public static List<ProdutoPedidoDTO> retornarProdutoPedidoDto() {
        ProdutoResponsePedidoDTO produtoResponsePedidoDTO = MockProduto.retornarProdutoResponsePedidoDTO();
        List<ProdutoPedidoDTO> lista = new ArrayList<>();

        ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO();
        produtoPedidoDTO.setProduto(produtoResponsePedidoDTO);
        produtoPedidoDTO.setQuantidade(1);
        lista.add(produtoPedidoDTO);

        return lista;
    }

    public static PedidoDTO retornaPedidoDto(){
        PedidoDTO pedidoDto= new PedidoDTO();
        pedidoDto.setIdPedido(1);

        Usuario usuario= MockUsuario.retornarUsuario();
        UsuarioDTO usuarioDto =MockUsuario.retornarUsuarioDTO(usuario);
        pedidoDto.setUsuario(usuarioDto);

        Endereco endereco=MockEndereco.retornarEndereco();
        EnderecoDTO enderecoDto= MockEndereco.retornarEnderecoDTO(endereco);
        pedidoDto.setEndereco(enderecoDto);

        Empresa empresa= MockEmpresa.retornarEmpresa();
        EmpresaDTO empresaDto= MockEmpresa.retornarEmpresaDTO(empresa);
        pedidoDto.setEmpresaDTO(empresaDto);

        pedidoDto.setCupom(new CupomDTO(1,"cupom10", TipoAtivo.S,"cupom 10 de desconto", BigDecimal.valueOf(10),empresa.getIdEmpresa()));
        pedidoDto.setProdutos(retornarProdutoPedidoDto());

        pedidoDto.setFormaPagamento(FormaPagamento.CREDITO);
        pedidoDto.setStatusPedido(StatusPedido.A_CAMINHO);
        pedidoDto.setDataDePedido(LocalDate.of(2024,02,19));
        pedidoDto.setDataEntrega(LocalDate.of(2024,02,22));
        pedidoDto.setPrecoFrete(BigDecimal.valueOf(12.0));
        pedidoDto.setTotal(BigDecimal.valueOf(44.0));
        pedidoDto.setPrecoCarrinho(BigDecimal.valueOf(32.0));
        pedidoDto.setCodigoDeRastreio("1V5387ES1UP8");

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

    public static PedidoXProduto retornaPedidoXProduto() {
        PedidoXProduto pedidoXProduto = new PedidoXProduto();
        ProdutoXPedidoPK pk = new ProdutoXPedidoPK();
        pk.setIdProduto(1);
        pk.setIdPedido(1);
        pedidoXProduto.setProdutoXPedidoPK(pk);
        Pedido pedido = MockPedido.retornaPedidoEntity();
        pedidoXProduto.setPedido(pedido);
        Produto produto = MockProduto.retornarProdutoEntity();
        pedidoXProduto.setProduto(produto);
        pedidoXProduto.setQuantidade(10);
        return pedidoXProduto;
    }
    public static List<PedidoXProduto> retornaListaProdutoXPedido(){
        return List.of(retornaPedidoXProduto(),retornaPedidoXProduto());
    }


}
