package com.vemser.dbc.searchorganic.service;
import com.vemser.dbc.searchorganic.exceptions.BancoDeDadosException;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.repository.EnderecoRepository;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository = new EnderecoRepository();

    public EnderecoService() {
    }

    public List<Endereco> getEnderecos() {
        try {
            return enderecoRepository.listar();
        } catch (Exception e) {
            System.out.println("Erro ao obter endereços: " + e.getMessage());
            System.out.println();
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

    public boolean atualizarEndereco(Integer id, Endereco novoEndereco) {
        try {
            boolean enderecoAtualizado = enderecoRepository.editar(id, novoEndereco);

            if (enderecoAtualizado) {
                System.out.println("Endereço atualizado com sucesso!");
                return true;
            } else {
                System.out.println("ID não encontrado.");
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar endereço: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.out.println("Erro ao atualizar endereço: " + e.getMessage());
            throw new RuntimeException(e);
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

    public Endereco excluirEndereco(int idUsuario) {
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
