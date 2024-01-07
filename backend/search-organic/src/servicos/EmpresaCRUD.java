package servicos;
import modelo.Empresa;
import modelo.Produto;
import modelo.Usuario;
import utils.validadores.ValidadorCNPJ;
import java.util.ArrayList;
import java.util.Iterator;

public class EmpresaCRUD {
    private static ArrayList<Empresa> empresas = new ArrayList<>();

    public static void criarEmpresa(String nomeFantasia, String cnpj, String razaoSocial,
                                    String inscricaoEstadual, String setor, ArrayList<Produto> produtos,
                                    Usuario usuario) {

        if (ValidadorCNPJ.validarCNPJ(cnpj)) {
            Empresa novaEmpresa = new Empresa(nomeFantasia, cnpj, razaoSocial, inscricaoEstadual,
                    setor, produtos, usuario);

            empresas.add(novaEmpresa);
            System.out.println("Empresa criada com sucesso.");
        } else {
            System.out.println("CNPJ inválido. A empresa não foi criada.");
        }
    }

    public static void exibirEmpresa(int id) {
        for (Empresa empresa : empresas) {
            if (empresa.getId() == id) {
                empresa.imprimir();
                return;
            }
        }
        System.out.println("Empresa não registrada.");
    }

    public void listarEmpresas() {
        for (Empresa empresa : empresas) {
            empresa.imprimir();
        }
    }

    public void atualizarEmpresa(int id, String novoNomeFantasia, String novoCnpj,
                                 String novaRazaoSocial, String novaInscricaoEstadual,
                                 String novoSetor, ArrayList<Produto> novosProdutos,
                                 Usuario novoUsuario) {

        for (Empresa empresa : empresas) {
            if (empresa.getId() == id) {
                if (ValidadorCNPJ.validarCNPJ(novoCnpj)) {
                    empresa.setNomeFantasia(novoNomeFantasia);
                    empresa.setCnpj(novoCnpj);
                    empresa.setRazaoSocial(novaRazaoSocial);
                    empresa.setInscricaoEstadual(novaInscricaoEstadual);
                    empresa.setSetor(novoSetor);
                    empresa.setProdutos(novosProdutos);
                    empresa.setUsuario(novoUsuario);
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
            if (empresa.getId() == id) {
                iterator.remove();
                System.out.println("Empresa com o ID " + id + " excluída.");
                return;
            }
        }
        System.out.println("Empresa não encontrada.");
    }
}
