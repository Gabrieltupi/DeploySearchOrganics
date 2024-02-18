package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoCreateDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoUpdateDTO;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;

import java.math.BigDecimal;
import java.util.List;

public class MocksProduto {

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
}
