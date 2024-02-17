package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.model.*;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import com.vemser.dbc.searchorganic.utils.UnidadeMedida;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class MockEmpresa {
    public static Empresa retornarEmpresa(){
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(new Random().nextInt());
        empresa.setCnpj("56318380000161");
        empresa.setInscricaoEstadual("231321");
        empresa.setNomeFantasia("Empresa Fantasia");
        empresa.setRazaoSocial("Fantasia S.A");
        empresa.setSetor("Agrícola");

        List<Produto> produtos = criarProdutos(empresa.getIdEmpresa());

        empresa.setProdutos(produtos);

        return empresa;
    }

    public static EmpresaDTO retornarEmpresaDTO(Empresa empresaEntity){
        EmpresaDTO empresaDTO = new EmpresaDTO(empresaEntity);
        return empresaDTO;
    }
    public static CreateEmpresaDTO retornarEmpresaCreateDTO(){
        return new CreateEmpresaDTO
                ("Empresa Fantasia", "56318380000161", "Fantasia S.A", "231321", "Agrícola");
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


    public static UpdateEmpresaDTO obterEmpresaUpdate(){
        UpdateEmpresaDTO updateEmpresaDTO = new UpdateEmpresaDTO();
        updateEmpresaDTO.setCnpj("213123131");
        updateEmpresaDTO.setSetor("Frutas");
        updateEmpresaDTO.setRazaoSocial("Nova Razao");
        updateEmpresaDTO.setNomeFantasia("Novo nomeFantasia");
        updateEmpresaDTO.setInscricaoEstadual("23135642");

        return updateEmpresaDTO;
    }

    public static Page<Empresa> retornarEmpresasPageable() {
        List<Empresa> empresas =  obterEmpresas();
        Page<Empresa> empresasPage = new PageImpl<>(empresas);

        return empresasPage;
    }

    public static List<Empresa> obterEmpresas(){
        List<Empresa> empresas = new ArrayList<>();
        return empresas;
    }
}
