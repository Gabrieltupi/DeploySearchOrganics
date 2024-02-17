package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.Usuario;
import java.util.Random;

public class MockEndereco {
    public static EnderecoDTO retornarEnderecoDTO(Endereco endereco){
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdEndereco(endereco.getIdEndereco());
        enderecoDTO.setIdUsuario(endereco.getUsuario().getIdUsuario());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setPais(endereco.getPais());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setRegiao(endereco.getRegiao());

        return enderecoDTO;
    }

    public static Endereco retornarEndereco(){
        Usuario usuario = MockUsuario.retornarUsuario();

        Endereco endereco = new Endereco();
        endereco.setIdEndereco(new Random().nextInt());
        endereco.setUsuario(usuario);
        endereco.setLogradouro("Avenida da Capoeira");
        endereco.setNumero("112");
        endereco.setComplemento("Apartamento 1");
        endereco.setCidade("Campinas");
        endereco.setEstado("São Paulo");
        endereco.setPais("Brasil");
        endereco.setRegiao("SP - Interior");
        endereco.setCep("19654-321");

        return endereco;
    }

    public static EnderecoCreateDTO retornarEnderecoCreateDTO(Endereco endereco) {
        EnderecoCreateDTO enderecoDTO = new EnderecoCreateDTO();
        endereco.setUsuario(endereco.getUsuario());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setPais(endereco.getPais());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setRegiao(endereco.getRegiao());

        return enderecoDTO;
    }

    public static EnderecoUpdateDTO retornarEnderecoUpdateDTO(Endereco endereco) {
        EnderecoUpdateDTO enderecoDTO = new EnderecoUpdateDTO();
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setPais(endereco.getPais());
        enderecoDTO.setCep(endereco.getCep());

        return enderecoDTO;
    }
}
