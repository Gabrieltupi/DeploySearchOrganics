package servicos;

import modelo.Empresa;
import modelo.Endereco;
import modelo.Produto;
import modelo.Usuario;
import utils.TipoCategoria;
import utils.validadores.ValidadorCNPJ;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class EmpresaCRUD {
    private static ArrayList<Empresa> empresas = new ArrayList<>();

    public static void criarEmpresa(String nomeFantasia, String cnpj, String razaoSocial,
                                    String inscricaoEstadual, String setor, ArrayList<Produto> produtos,
                                    Usuario usuario) {

        if (ValidadorCNPJ.validarCNPJ(cnpj)) {
            Empresa novaEmpresa = new Empresa(usuario.getLogin(), usuario.getPassword(), usuario.getNome(),
                    usuario.getSobrenome(), usuario.getEndereco(),
                    usuario.getDataNascimento(), nomeFantasia, cnpj, razaoSocial,
                    inscricaoEstadual, setor);

            empresas.add(novaEmpresa);
        } else {
            System.out.println("CNPJ inválido. A empresa não foi criada.");
        }
    }

    public static int criarEmpresa(String nomeFantasia, String cnpj, String razaoSocial,
                                   String inscricaoEstadual, String setor, Usuario usuario) {

        if (ValidadorCNPJ.validarCNPJ(cnpj)) {
            Empresa novaEmpresa = new Empresa(usuario.getLogin(), usuario.getPassword(), usuario.getNome(),
                    usuario.getSobrenome(), usuario.getEndereco(),
                    usuario.getDataNascimento(), nomeFantasia, cnpj, razaoSocial,
                    inscricaoEstadual, setor);
            empresas.add(novaEmpresa);
            return novaEmpresa.getUsuarioId();
        } else {
            System.out.println("CNPJ inválido. A empresa não foi criada.");
        }
        return 0;
    }

    public static void exibirEmpresa(int id) {
        for (Empresa empresa : empresas) {
            if (empresa.getUsuarioId() == id) {
                empresa.imprimir();
                return;
            }
        }
        System.out.println("Empresa não registrada.");
    }

    public void listarEmpresas() {
        for (Empresa empresa : empresas) {
            empresa.imprimir();
            System.out.println("-----------------");
        }
    }

    public void atualizarEmpresa(int id, String novoNomeFantasia, String novoCnpj,
                                 String novaRazaoSocial, String novaInscricaoEstadual,
                                 String novoSetor, ArrayList<Produto> novosProdutos,
                                 Usuario novoUsuario) {

        for (Empresa empresa : empresas) {
            if (empresa.getUsuarioId() == id) {
                if (ValidadorCNPJ.validarCNPJ(novoCnpj)) {
                    empresa.setLogin(novoUsuario.getLogin());
                    empresa.setPassword(novoUsuario.getPassword());
                    empresa.setNome(novoUsuario.getNome());
                    empresa.setSobrenome(novoUsuario.getSobrenome());
                    empresa.setEndereco(novoUsuario.getEndereco());
                    empresa.setDataNascimento(novoUsuario.getDataNascimento());
                    empresa.setNomeFantasia(novoNomeFantasia);
                    empresa.setCnpj(novoCnpj);
                    empresa.setRazaoSocial(novaRazaoSocial);
                    empresa.setInscricaoEstadual(novaInscricaoEstadual);
                    empresa.setSetor(novoSetor);
                    empresa.setProdutos(novosProdutos);
                    System.out.println("Empresa atualizada.");
                } else {
                    System.out.println("CNPJ inválido. A empresa não foi atualizada.");
                }
                return;
            }
        }
        System.out.println("Empresa não registrada.");
    }

    public void excluirEmpresa(int id) {
        Iterator<Empresa> iterator = empresas.iterator();
        while (iterator.hasNext()) {
            Empresa empresa = iterator.next();
            if (empresa.getUsuarioId() == id) {
                iterator.remove();
                System.out.println("Empresa com o ID " + id + " excluída.");
                return;
            }
        }
        System.out.println("Empresa não encontrada.");
    }

    // Opção 2.1 e 3.1
    public boolean imprimirProdutosDaLoja(int id) {
        for (Empresa empresa : empresas) {
            if (id == empresa.getUsuarioId()) {
                for (Produto produto : empresa.getProdutos()) {
                    System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                    System.out.println("-------------------------------------------------------------");
                }
                return true;
            }
        }
        System.out.println("Loja não encontrada!");
        return false;
    }

    // Opção 3
    public void lojasPorCategoria(TipoCategoria categoria) {
        for (Empresa empresa : empresas) {
            for (Produto produto : empresa.getProdutos()) {
                if (produto.getCategoriaT().equals(categoria)) {
                    System.out.println("id da loja: " + empresa.getUsuarioId() + " Nome: " + produto.getNome() + " Preço: " + produto.getPreco());
                    System.out.println("-------------------------------------------------------------");
                }
            }
        }
    }

    // Opção 2.1.2 e 2.1.3
    public boolean imprimirProdutosDaLojaPorCategoria(int id, TipoCategoria categoria) {
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
        System.out.println("Loja não encontrada!");
        return false;
    }

    // Opção 2
    public void lojas() {
        for (Empresa empresa : empresas) {
            System.out.println(empresa.getNomeFantasia());
            System.out.println();
            for (Produto produto : empresa.getProdutos()) {
                System.out.println("Nome: " + produto.getNome() + " Preço: " + produto.getPreco() + " Quantidade: " + produto.getQuantidade());
                System.out.println("-------------------------------------------------------------");
            }
            System.out.println();
        }
    }
}
