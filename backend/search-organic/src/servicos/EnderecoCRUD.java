package servicos;
import modelo.Endereco;

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
            if (!endereco.getEstado().equalsIgnoreCase("SP")){
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
                                          String cep, String cidade, String estado, String pais) {
        System.out.println(ValidadorCEP.getRegiao());
        if (ValidadorCEP.isCepValido(cep)) {
            System.out.println(ValidadorCEP.getRegiao());

            for(Endereco x: enderecos){
                if(id == x.getId()) {
                    x.setRegiao(ValidadorCEP.getRegiao());
                    if (logradouro != null) x.setLogradouro(logradouro);
                    if (numero != null) x.setNumero(numero);
                    if (complemento != null) x.setComplemento(complemento);
                    x.setCep(cep);
                    if (cidade != null) x.setCidade(cidade);
                    if (estado != null) x.setEstado(estado);
                    if (pais != null) x.setPais(pais);
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
