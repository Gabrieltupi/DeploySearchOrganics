package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.produto.ProdutoDTO;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockEmpresa {
    public static Empresa retornarEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        empresa.setCnpj("56318380000161");
        empresa.setInscricaoEstadual("231321");
        empresa.setNomeFantasia("Empresa Fantasia");
        empresa.setRazaoSocial("Fantasia S.A");
        empresa.setSetor("Agrícola");

        List<Produto> produtos = MockProduto.criarProdutos(empresa.getIdEmpresa());

        empresa.setProdutos(produtos);

        return empresa;
    }

    public static EmpresaDTO retornarEmpresaDTO(Empresa empresaEntity) {
        EmpresaDTO empresaDTO = new EmpresaDTO(empresaEntity);
        return empresaDTO;
    }

    public static CreateEmpresaDTO retornarEmpresaCreateDTO() {
        return new CreateEmpresaDTO
                ("Empresa Fantasia", "56318380000161", "Fantasia S.A", "231321", "Agrícola");
    }

    public static UpdateEmpresaDTO obterEmpresaUpdate() {
        UpdateEmpresaDTO updateEmpresaDTO = new UpdateEmpresaDTO();
        updateEmpresaDTO.setCnpj("213123131");
        updateEmpresaDTO.setSetor("Frutas");
        updateEmpresaDTO.setRazaoSocial("Nova Razao");
        updateEmpresaDTO.setNomeFantasia("Novo nomeFantasia");
        updateEmpresaDTO.setInscricaoEstadual("23135642");

        return updateEmpresaDTO;
    }

    public static Page<Empresa> retornarEmpresasPageable() {
        List<Empresa> empresas = obterEmpresas();
        Page<Empresa> empresasPage = new PageImpl<>(empresas);

        return empresasPage;
    }

    public static List<Empresa> obterEmpresas() {
        List<Empresa> empresas = new ArrayList<>();
        Empresa empresa = retornarEmpresa();
        empresas.add(empresa);
        return empresas;
    }

    public static EmpresaProdutosDTO retornarEmpresaProdutosDTO(Empresa empresa) {
        EmpresaProdutosDTO empresaProdutosDTO = new EmpresaProdutosDTO();
        empresaProdutosDTO.setCnpj(empresa.getCnpj());
        empresaProdutosDTO.setSetor(empresa.getSetor());
        empresaProdutosDTO.setIdEmpresa(empresa.getIdEmpresa());
        empresaProdutosDTO.setIdUsuario(empresa.getIdUsuario());
        empresaProdutosDTO.setRazaoSocial(empresa.getRazaoSocial());
        empresaProdutosDTO.setNomeFantasia(empresa.getNomeFantasia());
        empresaProdutosDTO.setInscricaoEstadual(empresa.getInscricaoEstadual());
        List<ProdutoDTO> produtoDTOS = MockProduto.retornarProdutosDTOs(empresa.getProdutos());
        empresaProdutosDTO.setProdutos(produtoDTOS);
        return empresaProdutosDTO;

    }

    public static Page<EmpresaProdutosDTO> retornarEmpresasComProdutos(Page<Empresa> empresasMock) {

        List<EmpresaProdutosDTO> listaEmpresaProdutosDTO = empresasMock.getContent()
                .stream()
                .map(empresa -> retornarEmpresaProdutosDTO(empresa))
                .toList();

        return new PageImpl<>(listaEmpresaProdutosDTO, empresasMock.getPageable(), empresasMock.getTotalElements());
    }
}
