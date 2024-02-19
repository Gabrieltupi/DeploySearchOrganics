package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.pedido.ProdutoCarrinhoCreate;

import com.vemser.dbc.searchorganic.dto.pedido.ProdutoPedidoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoResponsePedidoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockProduto {

    public static ProdutoCreateDTO retornarProdutoCreateDto(){
        ProdutoCreateDTO produtoCreateDTO= new ProdutoCreateDTO("Amora","roxa",new BigDecimal(5.44),new BigDecimal(20), TipoCategoria.FRUTAS,4, UnidadeMedida.KG,"", TipoAtivo.S);
        return produtoCreateDTO;
    }

    public static ProdutoUpdateDTO retornarProdutoUpdateDto(){
        ProdutoUpdateDTO produtoUpdateDTO= new ProdutoUpdateDTO(1,"Amora","verde",new BigDecimal(6.00),new BigDecimal(20), TipoCategoria.FRUTAS,4, UnidadeMedida.KG,"", TipoAtivo.S);
        return produtoUpdateDTO;
    }

    public static Produto retornarProdutoEntity(){
        Produto produto= new Produto();
        produto.setIdProduto(1);
        produto.setIdEmpresa(2);
        produto.setNome("amora");
        produto.setDescricao("roxa");
        produto.setPreco(new BigDecimal(5.44));
        produto.setQuantidade(new BigDecimal(20));
        produto.setCategoria(TipoCategoria.FRUTAS);
        produto.setTaxa(4);
        produto.setUnidadeMedida(UnidadeMedida.KG);
        produto.setUrlImagem("");
        produto.setTipoAtivo(TipoAtivo.S);
        return produto;
    }

    public static ProdutoDTO retornarProdutoDTO(){
        ProdutoDTO produtoDto= new ProdutoDTO();
        produtoDto.setIdProduto(1);
        produtoDto.setIdEmpresa(2);
        produtoDto.setNome("amora");
        produtoDto.setDescricao("roxa");
        produtoDto.setPreco(new BigDecimal(5.44));
        produtoDto.setQuantidade(new BigDecimal(20));
        produtoDto.setCategoria(TipoCategoria.FRUTAS);
        produtoDto.setTaxa(4);
        produtoDto.setUnidadeMedida(UnidadeMedida.KG);
        produtoDto.setUrlImagem("");
        produtoDto.setTipoAtivo(TipoAtivo.S);
        return produtoDto;
    }

    public static ProdutoResponsePedidoDTO retornarProdutoResponsePedidoDTO(){
        ProdutoResponsePedidoDTO produtoReponseDto= new ProdutoResponsePedidoDTO();
        produtoReponseDto.setIdProduto(1);
        produtoReponseDto.setIdEmpresa(2);
        produtoReponseDto.setNome("amora");
        produtoReponseDto.setDescricao("roxa");
        produtoReponseDto.setPreco(new BigDecimal(5.44));
        produtoReponseDto.setCategoria(TipoCategoria.FRUTAS);
        produtoReponseDto.setTaxa(4);
        produtoReponseDto.setUnidadeMedida(UnidadeMedida.KG);
        produtoReponseDto.setUrlImagem("");
        produtoReponseDto.setTipoAtivo(TipoAtivo.S);
        return produtoReponseDto;
    }

    public static List<Produto> retornarListaProdutos(){
        return List.of(retornarProdutoEntity(), retornarProdutoEntity(), retornarProdutoEntity());

    }


    public static Empresa retornarEmpresaEntity() {
        Empresa empresa= new Empresa();
        empresa.setIdEmpresa(2);
        empresa.setIdUsuario(1);
        empresa.setNomeFantasia("Fazeda");
        empresa.setRazaoSocial("vive");
        empresa.setInscricaoEstadual("123456");
        empresa.setSetor("alimento");
        empresa.setCnpj("123456789");
        return empresa;
    }

    public static EmpresaDTO retornarEmpresaDto() {
        EmpresaDTO empresaDTO= new EmpresaDTO();
        empresaDTO.setIdEmpresa(2);
        empresaDTO.setIdUsuario(1);
        empresaDTO.setNomeFantasia("Fazeda");
        empresaDTO.setRazaoSocial("vive");
        empresaDTO.setInscricaoEstadual("123456");
        empresaDTO.setSetor("alimento");
        empresaDTO.setCnpj("123456789");
        return empresaDTO;
    }

    public static List<ProdutoDTO> retornarProdutosDTOs(List<Produto> produtos){
        List<ProdutoDTO> produtoDTOS =  produtos.stream().map(produto -> {
            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setQuantidade(produto.getQuantidade());
            produtoDTO.setUrlImagem(produto.getUrlImagem());
            produtoDTO.setIdEmpresa(produto.getIdEmpresa());
            produtoDTO.setCategoria(produto.getCategoria());
            produtoDTO.setDescricao(produto.getDescricao());
            produtoDTO.setPreco(produto.getPreco());
            produtoDTO.setTipoAtivo(produto.getTipoAtivo());
            produtoDTO.setUnidadeMedida(produto.getUnidadeMedida());
            produtoDTO.setTaxa(produto.getTaxa());

            return produtoDTO;
        }).toList();

        return produtoDTOS;
    }

    public static  List<ProdutoPedidoDTO> retornaListaProdutoPedidoDTO(){
        List<ProdutoPedidoDTO> produtoPedidoDTOS = new ArrayList<>();
        ProdutoResponsePedidoDTO produto  = new ProdutoResponsePedidoDTO();
        produto.setNome("produto1");
        produto.setPreco(BigDecimal.valueOf(5));
        Integer quantidade=  5;
        ProdutoPedidoDTO produtoPedidoDTO = new ProdutoPedidoDTO(produto, quantidade);
        produtoPedidoDTOS.add(produtoPedidoDTO);

        return produtoPedidoDTOS;

    }

    public static List<Produto> criarProdutos(Integer idEmpresa){
        List<Produto> produtos = new ArrayList<>();
        Produto produto = new Produto();
        produto.setIdProduto(new Random().nextInt());
        produto.setQuantidade(BigDecimal.valueOf(5));
        produto.setUrlImagem("1.jpg");
        produto.setIdEmpresa(idEmpresa);
        produto.setCategoria(TipoCategoria.FRUTAS);
        produto.setDescricao("Produto 1");
        produto.setPreco(BigDecimal.valueOf(10));
        produto.setTipoAtivo(TipoAtivo.S);
        produto.setUnidadeMedida(UnidadeMedida.UN);
        produto.setTaxa(1d);
        produtos.add(produto);

        return produtos;
    }


    public static  ProdutoDTO retornarProdutoDTOPorEntity(Produto produto){
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setIdProduto(produto.getIdProduto());
        produtoDTO.setNome(produto.getNome());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setDescricao(produto.getDescricao());
        produtoDTO.setCategoria(produto.getCategoria());
        produtoDTO.setQuantidade(produto.getQuantidade());
        produtoDTO.setUrlImagem(produto.getUrlImagem());
        produtoDTO.setTaxa(produto.getTaxa());
        produtoDTO.setTipoAtivo(produto.getTipoAtivo());
        produtoDTO.setIdEmpresa(produto.getIdEmpresa());

        return produtoDTO;
    }

    public static ProdutoDTO retornarNovoProdutoPorUpdate(Integer id, ProdutoUpdateDTO produtoUpdateDTO) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setIdProduto(id);
        produtoDTO.setIdEmpresa(produtoUpdateDTO.getIdEmpresa());
        produtoDTO.setNome(produtoUpdateDTO.getNome());
        produtoDTO.setPreco(produtoUpdateDTO.getPreco());
        produtoDTO.setDescricao(produtoUpdateDTO.getDescricao());
        produtoDTO.setCategoria(produtoUpdateDTO.getCategoria());
        produtoDTO.setQuantidade(produtoUpdateDTO.getQuantidade());
        produtoDTO.setUrlImagem(produtoUpdateDTO.getUrlImagem());
        produtoDTO.setTaxa(produtoUpdateDTO.getTaxa());
        produtoDTO.setTipoAtivo(produtoUpdateDTO.getTipoAtivo());

        return produtoDTO;
    }
}
