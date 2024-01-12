package servicos;

import modelo.Empresa;
import modelo.Produto;
import utils.TipoCategoria;
import utils.validadores.ValidadorCNPJ;

import java.util.ArrayList;
import java.util.Iterator;
//EmpresaRepository
public class EmpresaServicos {
    private static ArrayList<Empresa> empresas = new ArrayList<>();

    public static void criarEmpresa(Empresa empresa) {
        try {
            if (ValidadorCNPJ.validarCNPJ(empresa.getCnpj())) {
                Empresa novaEmpresa = new Empresa(empresa.getLogin(), empresa.getPassword(), empresa.getNome(),
                        empresa.getSobrenome(), empresa.getEndereco(),
                        empresa.getDataNascimento(), empresa.getNomeFantasia(), empresa.getCnpj(), empresa.getRazaoSocial(),
                        empresa.getInscricaoEstadual(), empresa.getSetor());
                empresas.add(novaEmpresa);
            } else {
                throw new IllegalArgumentException("CNPJ inválido. Certifique-se de inserir um CNPJ válido para criar a empresa.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao criar empresa: " + e.getMessage());
        }
    }


    public static void exibirEmpresa(int id) {
        try {
            for (Empresa empresa : empresas) {
                if (empresa.getUsuarioId() == id) {
                    empresa.imprimir();
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao exibir empresa: " + e.getMessage());
        }
    }


    public void listarEmpresas() {
        try {
            for (Empresa empresa : empresas) {
                empresa.imprimir();
                System.out.println("-----------------");
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar empresas: " + e.getMessage());
        }
    }


    public void atualizarEmpresa(Empresa novaEmpresa) {
        try {
            for (Empresa empresa : empresas) {
                if (empresa.getUsuarioId() == novaEmpresa.getUsuarioId()) {
                    if (ValidadorCNPJ.validarCNPJ(novaEmpresa.getCnpj())) {
                        empresa.setLogin(novaEmpresa.getLogin());
                        empresa.setPassword(novaEmpresa.getPassword());
                        empresa.setNome(novaEmpresa.getNome());
                        empresa.setSobrenome(novaEmpresa.getSobrenome());
                        empresa.setEndereco(novaEmpresa.getEndereco());
                        empresa.setDataNascimento(novaEmpresa.getDataNascimento());
                        empresa.setNomeFantasia(novaEmpresa.getNomeFantasia());
                        empresa.setCnpj(novaEmpresa.getCnpj());
                        empresa.setRazaoSocial(novaEmpresa.getRazaoSocial());
                        empresa.setInscricaoEstadual(novaEmpresa.getInscricaoEstadual());
                        empresa.setSetor(novaEmpresa.getSetor());
                        empresa.setProdutos(novaEmpresa.getProdutos());
                        System.out.println("Empresa atualizada com sucesso.");
                    } else {
                        throw new IllegalArgumentException("CNPJ inválido. Certifique-se de inserir um CNPJ válido para atualizar a empresa.");
                    }
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar empresa: " + e.getMessage());
        }
    }


    public void excluirEmpresa(int id) {
        try {
            Iterator<Empresa> iterator = empresas.iterator();
            while (iterator.hasNext()) {
                Empresa empresa = iterator.next();
                if (empresa.getUsuarioId() == id) {
                    iterator.remove();
                    System.out.println("Empresa com o ID " + id + " excluída com sucesso.");
                    return;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir empresa: " + e.getMessage());
        }
    }


    // Opção 2.1 e 3.1
    public boolean imprimirProdutosDaLoja(int id) {
        try {
            if (empresas == null) {
                System.out.println("Lista de empresas está nula");
                return false;
            }

            for (Empresa empresa : empresas) {
                if (id == empresa.getUsuarioId()) {
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
                }
            }

            System.out.println("Nenhuma empresa encontrada com o ID fornecido.");
            return false;
        } catch (NullPointerException e) {
            System.out.println("Erro ao imprimir produtos da loja: " + e.getMessage());
            return false;
        }
    }





    // Opção 3
    public void lojasPorCategoria(TipoCategoria categoria) {
        try {
            for (Empresa empresa : empresas) {
                for (Produto produto : empresa.getProdutos()) {
                    if (produto.getCategoriaT().equals(categoria)) {
                        System.out.println("id da loja: " + empresa.getUsuarioId() + " Nome: " + produto.getNome() + " Preço: " + produto.getPreco());
                        System.out.println("-------------------------------------------------------------");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar lojas por categoria: " + e.getMessage());
        }
    }


    // Opção 2.1.2 e 2.1.3
    public boolean imprimirProdutosDaLojaPorCategoria(int id, TipoCategoria categoria) {
        try {
            for (Empresa empresa : empresas) {
                if (id == empresa.getUsuarioId()) {
                    for (Produto produto : empresa.getProdutos()) {
                        if (produto.getCategoriaT().equals(categoria)) {
                            System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                            System.out.println("-------------------------------------------------------------");
                        }
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao imprimir produtos da loja por categoria: " + e.getMessage());
            return false;
        }
        return false;
    }


    // Opção 2
    public void lojas() {
        try {
            for (Empresa empresa : empresas) {
                System.out.println(empresa.getNomeFantasia());
                System.out.println();
                for (Produto produto : empresa.getProdutos()) {
                    System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                    System.out.println("-------------------------------------------------------------");
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar lojas: " + e.getMessage());
        }
    }
}

