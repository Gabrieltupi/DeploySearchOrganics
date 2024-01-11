//package servicos;
//
//import modelo.DadosPessoais;
//import modelo.Endereco;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//
//public class DadosPessoaisCRUD {
//
//    private ArrayList<DadosPessoais> dadosPessoais;
//
//    public DadosPessoaisCRUD() {
//        this.dadosPessoais = new ArrayList<>();
//    }
//
//    public ArrayList<DadosPessoais> getDadosPessoais() {
//        return dadosPessoais;
//    }
//
//    public boolean adicionarDadosPessoais(DadosPessoais dadosPessoais) {
//        if (dadosPessoais != null) {
//            this.dadosPessoais.add(dadosPessoais);
//            return true;
//        }
//        return false;
//    }
//
//    public boolean atualizarDadosPessoais(int id, String nome, String sobrenome, Endereco endereco,
//                                          LocalDate dataNascimento) {
//        for (DadosPessoais pessoa : dadosPessoais) {
//            if (id == pessoa.getId()) {
//                pessoa.setNome(nome);
//                pessoa.setSobrenome(sobrenome);
//                pessoa.setEndereco(endereco);
//                pessoa.setDataNascimento(dataNascimento);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void imprimirDadosPessoais() {
//        for (DadosPessoais pessoa : dadosPessoais) {
//            System.out.println("Dados pessoais");
//            System.out.println(pessoa);
//        }
//    }
//
//    public boolean imprimirDadosPessoais(int id) {
//        for (DadosPessoais pessoa : dadosPessoais) {
//            if (pessoa.getId() == id) {
//                pessoa.imprimir();
//                return true;
//            }
//        }
//        System.out.println("ID não encontrado!!");
//        return false;
//    }
//
//    public boolean excluirDadosPessoais(int id) {
//        DadosPessoais pessoaRemover = null;
//        for (DadosPessoais pessoa : dadosPessoais) {
//            if (pessoa.getId() == id) {
//                pessoaRemover = pessoa;
//                break;
//            }
//        }
//        if (pessoaRemover != null) {
//            dadosPessoais.remove(pessoaRemover);
//            System.out.println("Dados do ID " + id + " foram excluídos!!");
//            return true;
//        } else {
//            System.out.println("ID não encontrado.");
//            return false;
//        }
//    }
//}
