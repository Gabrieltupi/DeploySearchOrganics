package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Produto;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import com.vemser.dbc.searchorganic.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ProdutoRepository produtoRepository;
    private final ObjectMapper objectMapper;

    private final ProdutoService produtoService;


    public Empresa adicionarEmpresa(Integer idUsuario, Empresa empresa) throws BancoDeDadosException {
        return empresaRepository.adicionar(empresa, idUsuario);
    }

    public Empresa buscarEmpresa(Integer id) throws BancoDeDadosException, RegraDeNegocioException {

        return empresaRepository.buscaPorId(id);


    }

//    public Empresa atualizarEmpresa(Integer idEmpresa, Empresa novaEmpresa) throws Exception {
//        empresaRepository.buscaPorId(idEmpresa);
//        return empresaRepository.editar(idEmpresa, novaEmpresa);
//    }


    public Empresa atualizarEmpresa(Integer idEmpresa, UpdateEmpresaDTO novaEmpresa) throws Exception {
        Empresa empresa = buscarEmpresa(idEmpresa);
        empresa.setNomeFantasia(novaEmpresa.getNomeFantasia());
        empresa.setInscricaoEstadual(novaEmpresa.getInscricaoEstadual());
        empresa.setCnpj(novaEmpresa.getCnpj());
        empresa.setSetor(novaEmpresa.getSetor());
        empresa.setRazaoSocial(novaEmpresa.getRazaoSocial());

        return empresaRepository.editar(idEmpresa, empresa);
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

    public EmpresaDTO preencherInformacoes(Empresa empresa) throws Exception {
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setIdEmpresa(empresa.getIdEmpresa());
        empresaDTO.setIdUsuario(empresa.getIdUsuario());
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

