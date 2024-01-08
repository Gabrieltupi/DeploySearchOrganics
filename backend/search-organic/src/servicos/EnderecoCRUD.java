package servicos;

import modelo.Endereco;
import utils.validadores.ValidadorCEP;

import java.util.ArrayList;

public class EnderecoCRUD {

    private ArrayList<Endereco> enderecos = new ArrayList<>();

    public EnderecoCRUD() {
    }

    public ArrayList<Endereco> getEnderecos() {
        return enderecos;
    }

    public boolean adicionarEndereco(Endereco endereco) {
        if (endereco != null && ValidadorCEP.isCepValido(endereco.getCep()) != null) {
            this.enderecos.add(endereco);
            return true;
        }
        System.out.println("CEP inválido!");
        return false;
    }

    public boolean atualizarEndereco(int id, String logradouro, String numero, String complemento,
                                     String cep, String cidade, String estado, String pais) {

        if (ValidadorCEP.isCepValido(cep) != null) {
            System.out.println(ValidadorCEP.isCepValido(cep));

            for (Endereco endereco : enderecos) {
                if (id == endereco.getId()) {
                    endereco.setRegiao(ValidadorCEP.isCepValido(cep));
                    if (logradouro != null) endereco.setLogradouro(logradouro);
                    if (numero != null) endereco.setNumero(numero);
                    if (complemento != null) endereco.setComplemento(complemento);
                    endereco.setCep(cep);
                    if (cidade != null) endereco.setCidade(cidade);
                    if (estado != null) endereco.setEstado(estado);
                    if (pais != null) endereco.setPais(pais);
                    return true;
                }
            }
        }
        System.out.println("CEP inválido");
        return false;
    }

    public void imprimirEnderecos() {
        for (Endereco endereco : enderecos) {
            System.out.println("Endereço:");
            System.out.println(endereco);
        }
    }

    public boolean imprimirEndereco(int id) {
        for (Endereco endereco : enderecos) {
            if (endereco.getId() == id) {
                System.out.println("Endereço do ID " + id);
                System.out.println(endereco);
                return true;
            }
        }
        System.out.println("ID não encontrado!!");
        return false;
    }

    public boolean excluirEndereco(int id) {
        for (Endereco endereco : enderecos) {
            if (endereco.getId() == id) {
                enderecos.remove(endereco);
                System.out.println("Endereço do ID " + id + " foi excluído!!");
                return true;
            }
        }
        System.out.println("ID não encontrado.");
        return false;
    }
}
