package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.EnderecoRepository;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public EnderecoDTO buscarEndereco(Integer idEndereco) throws Exception {

        Endereco endereco = this.getById(idEndereco);
        return mapEnderecoToDTO(endereco);
    }

    public Endereco getById(Integer id) throws Exception {
        Integer enderecoIdDoUsuarioLogado = enderecoRepository.findEnderecoIdByUserId(getIdLoggedUser())
                .orElseThrow(() -> new RegraDeNegocioException("Endereço do usuário logado não encontrado"));

        if (enderecoIdDoUsuarioLogado.equals(id) || isAdmin()) {
            return enderecoRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Endereço não encontrado"));
        } else {
            throw new RegraDeNegocioException("Só é possível retornar seus próprios dados.");
        }
    }


    public boolean isAdmin() {
        Integer userId = getIdLoggedUser();
        Integer count = enderecoRepository.existsAdminCargoByUserId(userId);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }


    public List<EnderecoDTO> listarEnderecos() {

        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream().map(endereco -> {
            EnderecoDTO enderecoDTO = objectMapper.convertValue(endereco, EnderecoDTO.class);
            enderecoDTO.setIdUsuario(endereco.getUsuario().getIdUsuario());
            return enderecoDTO;
        }).toList();

    }


    public EnderecoDTO adicionarEndereco(EnderecoCreateDTO enderecoDTO) throws Exception {
        try {
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
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao adicionar endereço: " + e.getMessage());
        }
    }


    public EnderecoDTO editarEndereco(Integer idEndereco, EnderecoUpdateDTO enderecoUpdateDTO) throws Exception {

        Integer loggedUserId = getIdLoggedUser();

        if (loggedUserId.equals(idEndereco) || isAdmin()) {


            Optional<Endereco> enderecoOptional = enderecoRepository.findById(idEndereco);

        if (enderecoOptional.isPresent()) {
            Endereco enderecoExistente = enderecoOptional.get();
            updateEnderecoFromDTO(enderecoExistente, enderecoUpdateDTO);
            Endereco enderecoAtualizado = enderecoRepository.save(enderecoExistente);
            return mapEnderecoToDTO(enderecoAtualizado);
        } else {
            throw new Exception("Endereço não encontrado");
        }

        } else {
            throw new RegraDeNegocioException("Apenas o usuário residente ou um administrador pode editar o endereço.");
        }
    }

    public void removerEndereco(Integer idEndereco) throws Exception {

        Integer loggedUserId = getIdLoggedUser();

        if (loggedUserId.equals(idEndereco) || isAdmin()) {


            if (enderecoRepository.existsById(idEndereco)) {
            enderecoRepository.deleteById(idEndereco);
        } else {
            throw new Exception("Endereço não encontrado");
        }

        } else {
            throw new RegraDeNegocioException("Apenas o usuário residente ou um administrador pode remover o endereço.");
        }
    }

    public List<EnderecoDTO> listarEnderecosPorUsuario(Integer idUsuario) throws Exception {

        Integer loggedUserId = getIdLoggedUser();

        if (loggedUserId.equals(idUsuario) || isAdmin()) {


            List<Endereco> enderecos = enderecoRepository.findAllByUsuarioIdUsuario(idUsuario);
        return enderecos.stream()
                .map(this::mapEnderecoToDTO)
                .collect(Collectors.toList());

        } else {
            throw new RegraDeNegocioException("Apenas o usuário dono da conta ou um administrador pode remover o usuário.");
        }
    }


    private Endereco mapCreateDTOToEntity(EnderecoCreateDTO enderecoCreateDTO) {
        Endereco endereco = new Endereco();
        endereco.setUsuario(usuarioRepository.findById(enderecoCreateDTO.getIdUsuario()).orElse(null));
        return endereco;
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

    public Page<EnderecoDTO> listarEnderecosPaginados(Pageable pageable) {
        Page<Endereco> enderecos = enderecoRepository.findAll(pageable);
        return enderecos.map(endereco -> {
            EnderecoDTO enderecoDTO = objectMapper.convertValue(endereco, EnderecoDTO.class);
            enderecoDTO.setIdUsuario(endereco.getUsuario().getIdUsuario());
            return enderecoDTO;
        });

    }
}
