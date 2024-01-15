package service;

import exceptions.BancoDeDadosException;
import model.Empresa;
import model.Produto;
import repository.EmpresaRepository;
import utils.TipoCategoria;
import utils.validadores.ValidadorCNPJ;

import java.util.ArrayList;


public class EmpresaService {
    private EmpresaRepository repository = new EmpresaRepository();

    public void criarEmpresa(Empresa empresa) {
        try {
            if (ValidadorCNPJ.validarCNPJ(empresa.getCnpj())) {
                Empresa novaEmpresa = new Empresa(empresa.getNomeFantasia(), empresa.getCnpj(), empresa.getRazaoSocial(),
                        empresa.getInscricaoEstadual(), empresa.getSetor(), empresa.getIdUsuario());
                repository.adicionar(novaEmpresa);
            } else {
                throw new IllegalArgumentException("CNPJ inválido. Certifique-se de inserir um CNPJ válido para criar a empresa.");
            }
        } catch (BancoDeDadosException bancoDeDadosException) {
            throw new RuntimeException(bancoDeDadosException.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao criar empresa: " + e.getMessage());
        }
    }


    public void exibirEmpresa(int id) {
        try {
            Empresa empresa = repository.buscaPorId(id);
            empresa.imprimir();
        } catch (BancoDeDadosException bancoDeDadosException) {
            throw new RuntimeException(bancoDeDadosException.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao exibir empresa: " + e.getMessage());
        }
    }

    public Empresa buscarEmpresa(int id) {
        try {
            Empresa empresa = repository.buscaPorId(id);
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
            ArrayList<Empresa> empresas = (ArrayList<Empresa>) repository.listar();
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


    public void atualizarEmpresa(Empresa novaEmpresa) {
        try {
            repository.editar(novaEmpresa.getIdEmpresa(), novaEmpresa);
        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }


    public void excluirEmpresa(int id) {
        try {
            if (repository.remover(id)) {
                System.out.println("Empresa com o ID " + id + " excluída com sucesso.");
                return;
            }
            System.err.println("Ocorreu um erro ao excluir a empresa");
        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao excluir empresa: " + e.getMessage());
        }
    }


    // Opção 2.1 e 3.1
    public boolean imprimirProdutosDaLoja(int id) {
        try {
            ProdutoService produtoService = new ProdutoService();
            produtoService.listarProdutosLoja(id);
            return true;
        } catch (NullPointerException e) {
            System.out.println("Erro ao imprimir produtos da loja: " + e.getMessage());
            return false;
        }
    }


    // Opção 3
    public void lojasPorCategoria(TipoCategoria categoria) {
        try {
            ArrayList<Empresa> empresas = (ArrayList<Empresa>) repository.listar();
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
        ProdutoService produtoService = new ProdutoService();
        try {
            Empresa empresa = repository.buscaPorId(id);
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
            ArrayList<Empresa> empresas = (ArrayList<Empresa>) repository.listar();
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
}
