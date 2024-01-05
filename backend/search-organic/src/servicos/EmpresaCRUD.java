package servicos;
import modelo.Empresa;
import utils.validadores.ValidadorCNPJ;
import java.util.ArrayList;
import java.util.Iterator;

public class EmpresaCRUD {
    private static ArrayList<Empresa> empresas = new ArrayList<>();

    public static void criarEmpresa(String id, String nomeFantasia, String cnpj, String razaoSocial,
                                    String inscricaoEstadual, String setor, ArrayList<String> produtos,
                                    String usuario) {

        if (ValidadorCNPJ.validarCNPJ(cnpj)) {
            Empresa novaEmpresa = new Empresa(id, nomeFantasia, cnpj, razaoSocial, inscricaoEstadual,
                    setor, produtos, usuario);

            empresas.add(novaEmpresa);
            System.out.println("Empresa criada com sucesso.");
        } else {
            System.out.println("CNPJ inválido. A empresa não foi criada.");
        }
    }

    // READ
    public static void exibirEmpresa(String id) {
        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(id)) {
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

    // UPDATE
    public void atualizarEmpresa(String id, String novoNomeFantasia, String novoCnpj,
                                 String novaRazaoSocial, String novaInscricaoEstadual,
                                 String novoSetor, ArrayList<String> novosProdutos,
                                 String novoUsuario) {

        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(id)) {
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

    // DELETE
    public void excluirEmpresa(String id) {
        Iterator<Empresa> iterator = empresas.iterator();
        while (iterator.hasNext()) {
            Empresa empresa = iterator.next();
            if (empresa.getId().equals(id)) {
                iterator.remove();
                System.out.println("Empresa com o ID " + id + " excluída.");
                return;
            }
        }
        System.out.println("Empresa não encontrada.");
    }
}
