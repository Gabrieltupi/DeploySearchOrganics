package servicos;

import modelo.Endereco;
import utils.validadores.ValidadorCEP;

import java.util.ArrayList;
import java.util.Iterator;

public class EnderecoServicos {

    private ArrayList<Endereco> enderecos = new ArrayList<>();

    public EnderecoServicos() {
    }

    public ArrayList<Endereco> getEnderecos() {
        return enderecos;
    }

    public boolean adicionarEndereco(Endereco endereco) {
        try {
            if (endereco != null && ValidadorCEP.isCepValido(endereco.getCep()) != null) {
                this.enderecos.add(endereco);
                return true;
            }
            throw new IllegalArgumentException("CEP inválido!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao adicionar endereço: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao adicionar endereço: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarEndereco(Endereco novoEndereco) {
        try {
            String regiao = ValidadorCEP.isCepValido(novoEndereco.getCep());
            if (regiao != null) {
                System.out.println(regiao);

                Iterator<Endereco> iterator = enderecos.iterator();
                while (iterator.hasNext()) {
                    Endereco endereco = iterator.next();
                    if (novoEndereco.getId() == endereco.getId()) {
                        endereco.setRegiao(regiao);
                        if (novoEndereco.getLogradouro() != null) endereco.setLogradouro(novoEndereco.getLogradouro());
                        if (novoEndereco.getNumero() != null) endereco.setNumero(novoEndereco.getNumero());
                        if (novoEndereco.getComplemento() != null) endereco.setComplemento(novoEndereco.getComplemento());
                        endereco.setCep(novoEndereco.getCep());
                        if (novoEndereco.getCidade() != null) endereco.setCidade(novoEndereco.getCidade());
                        if (novoEndereco.getEstado() != null) endereco.setEstado(novoEndereco.getEstado());
                        if (novoEndereco.getPais() != null) endereco.setPais(novoEndereco.getPais());
                        return true;
                    }
                }
            }
            throw new IllegalArgumentException("CEP inválido");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar endereço: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao atualizar endereço: " + e.getMessage());
            return false;
        }
    }

    public void imprimirEnderecos() {
        try {
            for (Endereco endereco : enderecos) {
                System.out.println("Endereço:");
                System.out.println(endereco);
                System.out.println("-------------------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println("Erro ao imprimir endereços: " + e.getMessage());
        }
    }

    public boolean imprimirEndereco(int id) {
        try {
            for (Endereco endereco : enderecos) {
                if (endereco.getId() == id) {
                    System.out.println("Endereço do ID " + id);
                    System.out.println(endereco);
                    return true;
                }
            }
            throw new IllegalArgumentException("ID não encontrado!!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao imprimir endereço: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao imprimir endereço: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirEndereco(int id) {
        try {
            Iterator<Endereco> iterator = enderecos.iterator();
            while (iterator.hasNext()) {
                Endereco endereco = iterator.next();
                if (endereco.getId() == id) {
                    iterator.remove();
                    System.out.println("Endereço do ID " + id + " foi excluído!!");
                    return true;
                }
            }
            throw new IllegalArgumentException("ID não encontrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir endereço: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao excluir endereço: " + e.getMessage());
            return false;
        }
    }
}
