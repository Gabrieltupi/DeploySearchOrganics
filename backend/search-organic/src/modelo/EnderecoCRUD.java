package modelo;
import java.util.ArrayList;
import utils.validadores.ValidadorCEP;

public class EnderecoCRUD {

    ArrayList <Endereco> enderecos = new ArrayList<>();

    public EnderecoCRUD(){}

    public ArrayList<Endereco> getEnderecos() {
        return enderecos;
    }

    public boolean adicionarEndereco(Endereco endereco){
        if(endereco != null) {
            if (!endereco.getEstado().equals("SP")){
                System.out.println("Ainda não atendemos neste estado");
                return false;
            }
            if (ValidadorCEP.isCepValido(endereco.getCep())) {
                endereco.setRegiao(ValidadorCEP.getRegiao());
                this.enderecos.add(endereco);
                return true;
            }
            System.out.println("CEP inválido!");
            return false;
        }
        return false;
    }

    public boolean atualizarEndereco(int id, String logradouro, String numero, String complemento,
                                          String cep, String cidade, String estado, String pais){
        System.out.println(ValidadorCEP.getRegiao());
        if (ValidadorCEP.isCepValido(cep)) {
            System.out.println(ValidadorCEP.getRegiao());
            for(Endereco x: enderecos){
                if(id == x.getId()){
                    x.setRegiao(ValidadorCEP.getRegiao());
                    x.setLogradouro(logradouro);
                    x.setNumero(numero);
                    x.setComplemento(complemento);
                    x.setCep(cep);
                    x.setCidade(cidade);
                    x.setEstado(estado);
                    x.setPais(pais);
                    return true;
                }
            }
        }
        return false;
    }

    public void imprimirEnderecos(){
        for (Endereco x: enderecos){
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
                System.out.println("Endereço do id " + id + " foi excluido!!");
                return true;
            }
        }
        System.out.println("ID não encontrada.");
        return false;
    }
}
