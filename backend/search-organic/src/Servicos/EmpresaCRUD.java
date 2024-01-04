package Servicos;
import Modelo.Empresa;
import java.util.ArrayList;

public class EmpresaCRUD {
    private ArrayList<Empresa> empresas = new ArrayList<>();

    public void criarEmpresa(String id, String nomeFantasia, String cnpj, String razaoSocial,
                             String inscricaoEstadual, String setor, ArrayList<String> produtos,
                             String usuario) {

        Empresa novaEmpresa = new Empresa(id, nomeFantasia, cnpj, razaoSocial, inscricaoEstadual,
                setor, produtos, usuario);

        empresas.add(novaEmpresa);
    }

    //READ
    public void exibirEmpresa(String id) {
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
    //UPDATE
    public void atualizarEmpresa(String id, String novoNomeFantasia, String novoCnpj,
                                 String novaRazaoSocial, String novaInscricaoEstadual,
                                 String novoSetor, ArrayList<String> novosProdutos,
                                 String novoUsuario) {
        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(id)) {
                empresa.setNomeFantasia(novoNomeFantasia);
                empresa.setCnpj(novoCnpj);
                empresa.setRazaoSocial(novaRazaoSocial);
                empresa.setInscricaoEstadual(novaInscricaoEstadual);
                empresa.setSetor(novoSetor);
                empresa.setProdutos(novosProdutos);
                empresa.setUsuario(novoUsuario);
                System.out.println("Empresa atualizada.");
                return;
            }
        }
        System.out.println("Empresa não registrada.");
    }

    //DELETE
    public void excluirEmpresa(String id) {
        for (Empresa empresa : empresas) {
            if (empresa.getId().equals(id)) {
                empresas.remove(empresa);
                System.out.println("Empresa com o ID "+ id +
                        " excluída.");
                return;
            }
        }
        System.out.println("Empresa não encontrada.");
}
}
