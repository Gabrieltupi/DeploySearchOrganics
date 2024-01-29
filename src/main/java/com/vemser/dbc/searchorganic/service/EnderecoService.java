package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoCreateDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoDTO;
import com.vemser.dbc.searchorganic.dto.endereco.EnderecoUpdateDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Endereco;
import com.vemser.dbc.searchorganic.repository.EnderecoRepository;
import com.vemser.dbc.searchorganic.utils.validadores.ValidadorCEP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public EnderecoDTO buscarEndereco(Integer idEndereco) throws Exception {
        log.info("[Endereço] Buscando");
        try {
            Endereco endereco = enderecoRepository.buscarPorId(idEndereco);
            return objectMapper.convertValue(endereco, EnderecoDTO.class);
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao buscar endereço: " + e.getMessage());
        }
    }

    public List<EnderecoDTO> listarEnderecos() throws Exception {
        log.info("[Endereço] Listando");
        try {
            List<Endereco> listaEnderecos = enderecoRepository.listar();
            return objectMapper.convertValue(listaEnderecos, objectMapper.getTypeFactory().constructCollectionType(List.class, EnderecoDTO.class));
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao listar endereços: " + e.getMessage());
        }
    }

    public EnderecoDTO adicionarEndereco(EnderecoCreateDTO enderecoDTO) throws Exception {
        log.info("[Endereço] Adicionando");
        try {
            String regiao = ValidadorCEP.isCepValido(enderecoDTO.getCep());
            if (regiao != null) {
                usuarioService.obterUsuarioPorId(enderecoDTO.getIdUsuario());

                Endereco enderecoEntity = objectMapper.convertValue(enderecoDTO, Endereco.class);
                enderecoEntity.setRegiao(regiao);
                Endereco enderecoAdicionado = enderecoRepository.adicionar(enderecoEntity);
                return objectMapper.convertValue(enderecoAdicionado, EnderecoDTO.class);
            }
            throw new RegraDeNegocioException("Erro ao adicionar endereço: CEP Inválido");
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao adicionar endereço: " + e.getMessage());
        }
    }

    public EnderecoDTO editarEndereco(Integer idEndereco, EnderecoUpdateDTO enderecoDTO) throws Exception {
        log.info("[Endereço] Editando");
        try {
            String regiao = ValidadorCEP.isCepValido(enderecoDTO.getCep());
            if (regiao != null) {
                buscarEndereco(idEndereco);
                usuarioService.obterUsuarioPorId(enderecoDTO.getIdUsuario());

                Endereco enderecoEntity = objectMapper.convertValue(enderecoDTO, Endereco.class);
                enderecoEntity.setRegiao(regiao);

                Endereco enderecoEditado = enderecoRepository.editar(idEndereco, enderecoEntity);

                enderecoEditado.setIdEndereco(idEndereco);
                return objectMapper.convertValue(enderecoEditado, EnderecoDTO.class);
            }
            throw new RegraDeNegocioException("Erro ao atualizar endereço: CEP Inválido");
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    public void removerEndereco(Integer idEndereco) throws Exception {
        log.info("[Endereço] Removendo");
        try {
            buscarEndereco(idEndereco);
            enderecoRepository.remover(idEndereco);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao remover endereço: " + e.getMessage());
        }
    }

    public List<EnderecoDTO> listarEnderecosPorUsuario(@PathVariable("idUsuario") Integer idUsuario) throws Exception {
        log.info("[Endereço] Listando por usuário");
        try {
            usuarioService.obterUsuarioPorId(idUsuario);
            List<Endereco> listaEnderecos = enderecoRepository.listarPorUsuario(idUsuario);
            return objectMapper.convertValue(listaEnderecos, objectMapper.getTypeFactory().constructCollectionType(List.class, EnderecoDTO.class));
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao listar endereços: " + e.getMessage());
        }
    }

    public void removerEnderecosPorUsuario(@PathVariable("idUsuario") Integer idUsuario) throws Exception {
        log.info("[Endereço] Removendo");
        try {
            usuarioService.obterUsuarioPorId(idUsuario);
            enderecoRepository.removerPorUsuario(idUsuario);
        } catch (RegraDeNegocioException e) {
            throw new RegraDeNegocioException(e.getMessage());
        } catch (Exception e) {
            throw new RegraDeNegocioException("Erro ao remover endereços: " + e.getMessage());
        }
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
}
