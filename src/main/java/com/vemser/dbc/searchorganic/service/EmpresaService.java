package com.vemser.dbc.searchorganic.service;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import com.vemser.dbc.searchorganic.utils.TipoCategoria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ProdutoRepository produtoRepository;

   private final ProdutoService produtoService;
    public EmpresaService(EmpresaRepository empresaRepository, ProdutoService produtoService,ProdutoRepository produtoRepository){
        this.empresaRepository = empresaRepository;
        this.produtoService = produtoService;
        this.produtoRepository = produtoRepository;
    }


    public Empresa adicionarEmpresa(Integer idUsuario,Empresa empresa) throws BancoDeDadosException {
        return empresaRepository.adicionar(empresa,idUsuario);
    }

    public Empresa buscarEmpresa(int id) {
        try {
            Empresa empresa = empresaRepository.buscaPorId(id);
            return empresa;
        } catch (BancoDeDadosException bancoDeDadosException) {
            throw new RuntimeException(bancoDeDadosException.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao exibir empresa: " + e.getMessage());
            return null;
        }
    }

//    public Empresa atualizarEmpresa(Integer idEmpresa, Empresa novaEmpresa) throws Exception {
//        empresaRepository.buscaPorId(idEmpresa);
//        return empresaRepository.editar(idEmpresa, novaEmpresa);
//    }



    public Empresa atualizarEmpresa(Integer idEmpresa, UpdateEmpresaDTO novaEmpresa) {
        Empresa empresa = buscarEmpresa(idEmpresa);
        empresa.setIdEmpresa(novaEmpresa.getIdEmpresa());
        empresa.setIdUsuario(novaEmpresa.getIdUsuario());
        empresa.setProdutos(novaEmpresa.getProdutos());
        empresa.setNomeFantasia(novaEmpresa.getNomeFantasia());
        empresa.setInscricaoEstadual(novaEmpresa.getInscricaoEstadual());
        empresa.setCnpj(novaEmpresa.getCnpj());
        empresa.setSetor(novaEmpresa.getSetor());
        empresa.setRazaoSocial(novaEmpresa.getRazaoSocial());
        return empresa;
    }

    public void excluirEmpresa(int id) {
        try {
            empresaRepository.remover(id);
            System.out.println("Empresa com o ID " + id + " excluída com sucesso.");
        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        } catch (Exception e) {
            System.err.println("Erro ao excluir empresa: " + e.getMessage());
        }
    }

    public EmpresaDTO preencherInformacoes(Empresa empresa) throws Exception{
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setIdEmpresa(empresa.getIdEmpresa());
        empresaDTO.setIdUsuario(empresa .getIdUsuario());
        empresaDTO.setProdutos(empresa.getProdutos());
        empresaDTO.setNomeFantasia(empresa.getNomeFantasia());
        empresaDTO.setInscricaoEstadual(empresa.getInscricaoEstadual());
        empresaDTO.setCnpj(empresa.getCnpj());
        empresaDTO.setSetor(empresa.getSetor());
        empresaDTO.setRazaoSocial(empresa.getRazaoSocial());
        return empresaDTO;
    }

    public List<Produto> listarProdutosDaLoja(Integer idEmpresa) throws BancoDeDadosException, RegraDeNegocioException {
        Empresa empresa = empresaRepository.buscaPorId(idEmpresa);

        if (empresa != null) {
            return produtoRepository.listarProdutosLoja(idEmpresa);
        }
        return List.of();
    }
}

