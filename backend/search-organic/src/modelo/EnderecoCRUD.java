package modelo;
import java.util.ArrayList;

public class EnderecoCRUD {

    ArrayList <Endereco> enderecos = new Arraylist<>();

    public EnderecoCRUD(){}

    public boolean adicionarEndereco(Endereco endereco){
        if(endereco != null) {
            this.enderecos.add(endereco);
            return true;
        }
        return false;
    }

    public boolean atualizarEndereco(int id, String logradouro, String numero, String complemento,
                                          String cep, String cidade, String estado, String pais){
        for(Endereco x: enderecos){
            if(id == x.getId()){
                x.setLogradouro(logradouro);
                x.setLogradouro(numero);
                x.setLogradouro(complemento);
                x.setLogradouro(cep);
                x.setLogradouro(cidade);
                x.setLogradouro(estado);
                x.setLogradouro(pais);
                return true;
            }
        }
        return false;
    }

    public void imprimirEnderecos(){
        for (Endereco x: dadosPessoais){
            System.out.println("Dados pessoais");
            System.out.println(x);

        }
    }

    public boolean imprimirEndereco(int id){
        for (Endereco x: enderecos){
            if(x.getId() == id) {
                System.out.println("Endereço do id " + id);
                System.out.println(x);
                return true;
            }
        }
        System.out.println("ID não encontrado!!");
        return false;
    }

    public boolean excluirEndereco(int id) {
        for (Endereco x: enderecos) {
            if (x.getId() == id) {
                enderecos.remove(x);
                System.out.println("Endereço do id " + id + "foi excluido!!");
                return true;
            }
        }
        System.out.println("ID não encontrada.");
        return false;
    }
}
