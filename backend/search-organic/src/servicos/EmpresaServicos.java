package servicos;

import exceptions.BancoDeDadosException;
import modelo.Empresa;
import modelo.Produto;
import repository.EmpresaRepository;
import utils.TipoCategoria;
import utils.validadores.ValidadorCNPJ;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//EmpresaRepository
public class EmpresaServicos {
    private EmpresaRepository repository = new EmpresaRepository();

    public void criarEmpresa(Empresa empresa) {
        try {
            if (ValidadorCNPJ.validarCNPJ(empresa.getCnpj())) {
                Empresa novaEmpresa = new Empresa(empresa.getLogin(), empresa.getPassword(), empresa.getNome(),
                        empresa.getSobrenome(), empresa.getEndereco(),
                        empresa.getDataNascimento(), empresa.getNomeFantasia(), empresa.getCnpj(), empresa.getRazaoSocial(),
                        empresa.getInscricaoEstadual(), empresa.getSetor());
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
            repository.editar(novaEmpresa.getId_empresa(), novaEmpresa);
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
            Empresa empresa = repository.buscaPorId(id);
            ArrayList<Produto> produtos = empresa.getProdutos();

            if (produtos.isEmpty()) {
                System.out.println("A empresa não possui produtos.");
                return false;
            }

            for (Produto produto : produtos) {
                System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                System.out.println("-------------------------------------------------------------");
            }
            return true;
        } catch (NullPointerException e) {
            System.out.println("Erro ao imprimir produtos da loja: " + e.getMessage());
            return false;
        } catch (BancoDeDadosException bdEx) {
            throw new RuntimeException(bdEx.getMessage());
        }
    }


    // Opção 3
    public void lojasPorCategoria(TipoCategoria categoria) {
        try {
            ArrayList<Empresa> empresas = (ArrayList<Empresa>) repository.listar();
            for (Empresa empresa : empresas) {
                for (Produto produto : empresa.getProdutos()) {
                    if (produto.getCategoriaT().equals(categoria)) {
                        System.out.println("id da loja: " + empresa.getUsuarioId() + " Nome: " + produto.getNome() + " Preço: " + produto.getPreco());
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
    public boolean imprimirProdutosDaLojaPorCategoria(int id, TipoCategoria categoria) {
        try {
            Empresa empresa = repository.buscaPorId(id);
            for (Produto produto : empresa.getProdutos()) {
                if (produto.getCategoriaT().equals(categoria)) {
                    System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                    System.out.println("-------------------------------------------------------------");
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

