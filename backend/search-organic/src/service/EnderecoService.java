package service;

import exceptions.BancoDeDadosException;
import model.Endereco;
import repository.EnderecoRepository;
import utils.validadores.ValidadorCEP;

import java.util.ArrayList;
import java.util.List;

public class EnderecoService {

    private EnderecoRepository enderecoRepository = new EnderecoRepository();

    public EnderecoService() {
    }

    public List<Endereco> getEnderecos() {
        try {
            return enderecoRepository.listar();
        } catch (Exception e) {
            System.out.println("Erro ao obter endereços: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public Endereco adicionarEndereco(Endereco endereco) {
        try {
            if (endereco != null && ValidadorCEP.isCepValido(endereco.getCep()) != null) {
             return enderecoRepository.adicionar(endereco);
            }
          throw new IllegalArgumentException("CEP inválido!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao adicionar endereço: " + e.getMessage());
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao adicionar endereço: " + e.getMessage());

        }
        return endereco;
    }

    public boolean atualizarEndereco(Endereco novoEndereco) {
        try {
            String regiao = ValidadorCEP.isCepValido(novoEndereco.getCep());
            System.out.println(novoEndereco.getCep());
            System.out.println(regiao);
            if (regiao != null) {
                System.out.println(regiao);

                if (enderecoRepository.editar(novoEndereco.getId(), novoEndereco)) {
                    return true;
                }
                throw new IllegalArgumentException("ID não encontrado.");
            }
            throw new IllegalArgumentException("CEP inválido!");
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
            List<Endereco> enderecos = enderecoRepository.listar();
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
            Endereco endereco = enderecoRepository.buscarPorId(id);
            if (endereco != null) {
                System.out.println("Endereço do ID " + id);
                System.out.println(endereco);
                return true;
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

    public Endereco excluirPorIdUsuario(int idUsuario) {
        try {
            Endereco endereco =  enderecoRepository.buscarPorUsuarioId(idUsuario);
            if (endereco != null) {
                enderecoRepository.remover(endereco.getId());
                return endereco;
            }
            throw new IllegalArgumentException("ID não encontrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao excluir endereço: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Erro inesperado ao excluir endereço: " + e.getMessage());
            return null;
        }
    }
    public boolean verificaSeUsuarioPossuiEndereco (Integer idUsuario) {
        try {
            return     enderecoRepository.verificaUsuarioTemEndereco(idUsuario);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e);
        }
    }
    public Endereco getEndereco(Integer idUsuario){
        try {
            return enderecoRepository.buscarPorUsuarioId(idUsuario);
        } catch (BancoDeDadosException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
