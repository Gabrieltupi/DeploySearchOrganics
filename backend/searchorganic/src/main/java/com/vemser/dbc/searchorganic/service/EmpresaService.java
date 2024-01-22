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


    public Empresa adicionarEmpresa(Empresa empresa) throws BancoDeDadosException {
        return empresaRepository.adicionar(empresa);
    }



    public Empresa exibirEmpresa(int id) {
        try {
            Empresa empresa = empresaRepository.buscaPorId(id);
            empresa.imprimir();
            return empresa;
        } catch (BancoDeDadosException bancoDeDadosException) {
            throw new RuntimeException(bancoDeDadosException.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao exibir empresa: " + e.getMessage());
            return null;
        }
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

    public void listarEmpresas() {

        try {
            ArrayList<Empresa> empresas = (ArrayList<Empresa>) empresaRepository.listar();
            for (Empresa empresa : empresas) {
                empresa.imprimir();
                System.out.println("-----------------");
            }
        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao listar empresas: " + e.getMessage());
        }
    }


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



    // Opção 2.1 e 3.1
    public List<Produto> listarEImprimirProdutosDaLoja(int idLoja) {
        try {
            List<Produto> produtos = produtoService.listarProdutosLoja(idLoja);

            // Imprime os produtos
            for (Produto produto : produtos) {
                System.out.println(produto);
            }

            return produtos;
        } catch (BancoDeDadosException e) {
            System.out.println("Erro ao listar ou imprimir produtos da loja: " + e.getMessage());
            return Collections.emptyList();
        } catch (NullPointerException e) {
            System.out.println("Erro ao listar ou imprimir produtos da loja: " + e.getMessage());
            return Collections.emptyList();
        }
    }



    // Opção 3
    public void lojasPorCategoria(TipoCategoria categoria) {
        try {
            ArrayList<Empresa> empresas = (ArrayList<Empresa>) empresaRepository.listar();
            for (Empresa empresa : empresas) {
                for (Produto produto : empresa.getProdutos()) {
                    if (produto.getCategoriaT().equals(categoria)) {
                        System.out.println("id da loja: " + empresa.getIdUsuario() + " Nome: " + produto.getNome() + " Preço: " + produto.getPreco());
                        System.out.println("-------------------------------------------------------------");
                    }
                }
            }
        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao listar lojas por categoria: " + e.getMessage());
        }
    }


    // Opção 2.1.2 e 2.1.3
    //Agora recebe o id da empresa
    public boolean imprimirProdutosDaLojaPorCategoria(int id, int categoria) {
        try {
            Empresa empresa = empresaRepository.buscaPorId(id);
            for (Produto produto : produtoService.buscarProdutos()) {
                if (produto.getIdEmpresa() == empresa.getIdEmpresa()) {
                    System.out.println(produto.getCategoriaT());
                    if (produto.getCategoriaT().ordinal() == categoria) {
                        System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                        System.out.println("-------------------------------------------------------------");
                    }
                }
            }
            return true;

        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao imprimir produtos da loja por categoria: " + e.getMessage());
            return false;
        }
    }


    // Opção 2
    public void lojas() {
        try {
            ArrayList<Empresa> empresas = (ArrayList<Empresa>) empresaRepository.listar();
            for (Empresa empresa : empresas) {
                System.out.println(empresa.getNomeFantasia());
                System.out.println();
                for (Produto produto : empresa.getProdutos()) {
                    System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                    System.out.println("-------------------------------------------------------------");
                }
                System.out.println();
            }
        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao listar lojas: " + e.getMessage());
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

