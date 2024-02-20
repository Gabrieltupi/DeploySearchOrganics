package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.EnderecoRepository;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public EnderecoDTO buscarEndereco(Integer idEndereco) throws Exception {
        Endereco endereco= this.getById(idEndereco);
        Integer loggedUserId = usuarioService.getIdLoggedUser();

        if (endereco.getUsuario().getIdUsuario().equals(loggedUserId) || usuarioService.isAdmin()) {
            return mapEnderecoToDTO(endereco);
        } else {
            throw new RegraDeNegocioException("Só é possível retornar seus próprios dados.");
        }
    }

    public Page<EnderecoDTO> listarEnderecosPaginados(Pageable pageable) {
        Page<Endereco> enderecos = enderecoRepository.findAll(pageable);
        return enderecos.map(endereco -> {
            EnderecoDTO enderecoDTO = objectMapper.convertValue(endereco, EnderecoDTO.class);
            enderecoDTO.setIdUsuario(endereco.getUsuario().getIdUsuario());
            return enderecoDTO;
        });
    }



    public EnderecoDTO adicionarEndereco(EnderecoCreateDTO enderecoDTO) throws Exception {
        String regiao = ValidadorCEP.isCepValido(enderecoDTO.getCep());

        if (regiao != null) {
            Usuario usuario = usuarioService.obterUsuarioPorId(enderecoDTO.getIdUsuario());
            Endereco enderecoEntity = objectMapper.convertValue(enderecoDTO, Endereco.class);
            enderecoEntity.setRegiao(regiao);
            enderecoEntity.setUsuario(usuario);
            Endereco enderecoAdicionado = enderecoRepository.save(enderecoEntity);
            EnderecoDTO enderecoDTO1 = objectMapper.convertValue(enderecoAdicionado, EnderecoDTO.class);
            enderecoDTO1.setIdUsuario(enderecoDTO.getIdUsuario());
            return enderecoDTO1;
        }

        throw new RegraDeNegocioException("Erro ao adicionar endereço: CEP Inválido");
    }


    public EnderecoDTO editarEndereco(Integer idEndereco, EnderecoUpdateDTO enderecoUpdateDTO) throws Exception {
        Endereco endereco = getById(idEndereco);

        updateEnderecoFromDTO(endereco, enderecoUpdateDTO);
        Endereco enderecoAtualizado = enderecoRepository.save(endereco);
        return mapEnderecoToDTO(enderecoAtualizado);
    }

    public void removerEndereco(Integer idEndereco) throws Exception {
        getById(idEndereco);
        enderecoRepository.deleteById(idEndereco);
    }

    public List<EnderecoDTO> listarEnderecosPorUsuario(Integer idUsuario) throws Exception {
            if(usuarioService.getIdLoggedUser().equals(idUsuario)) {

                List<Endereco> enderecos = enderecoRepository.findAllByUsuarioIdUsuario(idUsuario);
                return enderecos.stream()
                        .map(this::mapEnderecoToDTO)
                        .collect(Collectors.toList());
            }
            throw new RegraDeNegocioException("Acesso a apenas seus dados");
    }

    private EnderecoDTO mapEnderecoToDTO(Endereco endereco) {
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setIdEndereco(endereco.getIdEndereco());
        enderecoDTO.setIdUsuario(endereco.getUsuario().getIdUsuario());
        enderecoDTO.setLogradouro(endereco.getLogradouro());
        enderecoDTO.setNumero(endereco.getNumero());
        enderecoDTO.setComplemento(endereco.getComplemento());
        enderecoDTO.setCep(endereco.getCep());
        enderecoDTO.setCidade(endereco.getCidade());
        enderecoDTO.setEstado(endereco.getEstado());
        enderecoDTO.setPais(endereco.getPais());
        enderecoDTO.setRegiao(endereco.getRegiao());
        return enderecoDTO;
    }

    private void updateEnderecoFromDTO(Endereco endereco, EnderecoUpdateDTO enderecoUpdateDTO) {
        endereco.setLogradouro(enderecoUpdateDTO.getLogradouro());
        endereco.setNumero(enderecoUpdateDTO.getNumero());
        endereco.setComplemento(enderecoUpdateDTO.getComplemento());
        endereco.setCep(enderecoUpdateDTO.getCep());
        endereco.setCidade(enderecoUpdateDTO.getCidade());
        endereco.setEstado(enderecoUpdateDTO.getEstado());
        endereco.setPais(enderecoUpdateDTO.getPais());
    }

    public String getMensagemEnderecoEmail(Endereco endereco) {
        String mensagem = String.format("""
                        Logradouro: %s  <br>
                        Número: %s       <br>
                        Complemento: %s   <br>
                        CEP: %s           <br>
                        Cidade: %s        <br>
                        Estado: %s        <br>
                        País: %s           <br>
                        """,
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCep(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getPais()
        );
        return mensagem;
    }

    public Endereco getById(Integer id) throws Exception {
        return enderecoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));
    }
}
