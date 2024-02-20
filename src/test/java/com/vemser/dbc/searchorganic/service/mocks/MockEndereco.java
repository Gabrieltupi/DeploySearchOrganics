package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.Usuario;

import java.util.List;
import java.util.Random;

public class MockEndereco {
    public static Endereco retornarEndereco(){
        Usuario usuario = MockUsuario.retornarUsuario();

        Endereco endereco = new Endereco();
        endereco.setIdEndereco(1);
        endereco.setUsuario(usuario);
        endereco.setLogradouro("Avenida da Capoeira");
        endereco.setNumero("112");
        endereco.setComplemento("Apartamento 1");
        endereco.setCidade("Campinas");
        endereco.setEstado("São Paulo");
        endereco.setPais("Brasil");
        endereco.setRegiao("SP - Interior");
        endereco.setCep("19654321");

        return endereco;
    }

    public static EnderecoDTO retornarEnderecoDTO(Endereco endereco){
        return new EnderecoDTO(endereco);
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

    public static List<Endereco> retornarEnderecos(Endereco endereco) {
        return List.of(endereco, endereco, endereco);
    }

    public static List<EnderecoDTO> retornarEnderecosDTO(EnderecoDTO enderecoDTO) {
        return List.of(enderecoDTO, enderecoDTO, enderecoDTO);
    }
}
