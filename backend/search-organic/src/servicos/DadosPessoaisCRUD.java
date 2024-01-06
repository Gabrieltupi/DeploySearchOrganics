package servicos;

import modelo.DadosPessoais;
import modelo.Endereco;

import java.util.ArrayList;
import java.util.Date;

public class DadosPessoaisCRUD {

    ArrayList <DadosPessoais> dadosPessoais = new ArrayList<>();

    public DadosPessoaisCRUD(){}

    public ArrayList<DadosPessoais> getDadosPessoais() {
        return dadosPessoais;
    }

    public boolean adicionarDadosPessoais(DadosPessoais dadosPessoais){
        if(dadosPessoais != null) {
            this.dadosPessoais.add(dadosPessoais);
            return true;
        }
        return false;
    }

    public boolean atualizarDadosPessoais(int id, String nome, String sobrenome, Endereco endereco,
                                           Date dataNascimento){
        for(DadosPessoais x: dadosPessoais){
            if(id == x.getId()){
                x.setNome(nome);
                x.setSobrenome(sobrenome);
                x.setEndereco(endereco);
                x.setDataNascimento(dataNascimento);
                return true;
            }
        }
        return false;
    }

    public void imprimirDadosPessoais(){
        for (DadosPessoais x: dadosPessoais){
            System.out.println("Dados pessoais");
            System.out.println(x);

        }
    }

    public boolean imprimirDadosPessoais(int id){
        for (DadosPessoais x: dadosPessoais){
            if(x.getId() == id) {
                System.out.println("Dados pessoais do id " + id);
                System.out.println(x);
                return true;
            }
        }
        System.out.println("ID não encontrado!!");
        return false;
    }

    public boolean excluirDadosPessoais(int id) {
        for (DadosPessoais x : dadosPessoais) {
            if (x.getId() == id) {
                dadosPessoais.remove(x);
                System.out.println("Dados do id " + id + "foi excluido!!");
                return true;
            }
        }
        System.out.println("ID não encontrada.");
        return false;
    }
}
