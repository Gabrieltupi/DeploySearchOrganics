package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioCreateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Carteira;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.CargoRepository;
import com.vemser.dbc.searchorganic.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final CargoRepository cargoRepository;

    public Usuario criarUsuario(Usuario usuario) throws Exception {
        try {
            Usuario novoUsuario = usuarioRepository.save(usuario);

            Map<String, Object> dadosEmail = new HashMap<>();
            dadosEmail.put("nomeUsuario", novoUsuario.getNome());
            dadosEmail.put("mensagem", "Bem-vindo ao nosso serviço");
            dadosEmail.put("email", novoUsuario.getEmail());

            emailService.sendEmail(dadosEmail, "Bem-vindo ao nosso serviço", novoUsuario.getEmail());

            return novoUsuario;
        } catch (DataIntegrityViolationException e) {
            throw new Exception("Erro ao criar o usuário devido a violação de integridade: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Erro ao criar o usuário: " + e.getMessage(), e);
        }
    }


    public List<Usuario> exibirTodos() throws Exception {
        Integer loggedUserId = getIdLoggedUser();
        if ( isAdmin()) {
            return usuarioRepository.findAll();
        } else {
            throw new RegraDeNegocioException("Apenas o  administrador pode ver a lista inteira do banco de dados de usuário.");
        }

    }

    public Usuario obterUsuarioPorId(Integer id) throws Exception {
        if(getIdLoggedUser().equals(id)||isAdmin()){
            return usuarioRepository.getById(id);
       }else{
            throw new RegraDeNegocioException("Só é possivel retornar seus próprios dados.");
        }
    }


    public UsuarioDTO obterUsuarioLogado() throws Exception {
        Usuario usuario = getLoggedUser();
        return objectMapper.convertValue(usuario, UsuarioDTO.class);
    }

    public boolean isAdmin() {
        Integer userId = getIdLoggedUser();
        Integer count = usuarioRepository.existsAdminCargoByUserId(userId);

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    public Usuario getLoggedUser() throws Exception {
        Integer userId = getIdLoggedUser();
        return findById(userId);
    }

    public Usuario editarUsuario(int usuarioId, Usuario usuario) throws Exception {
        try {

            Usuario usuarioEntity = obterUsuarioPorId(usuarioId);

            usuarioEntity.setLogin(usuario.getLogin());
            usuarioEntity.setEmail(usuario.getEmail());
            usuarioEntity.setCpf(usuario.getCpf());
            usuarioEntity.setSenha(usuario.getSenha());
            usuarioEntity.setDataNascimento(usuario.getDataNascimento());
            usuarioEntity.setNome(usuario.getNome());
            usuarioEntity.setSobrenome(usuario.getSobrenome());


            usuarioRepository.save(usuarioEntity);

            usuario.setIdUsuario(usuarioId);

            Map<String, Object> dadosEmail = new HashMap<>();
            dadosEmail.put("nomeUsuario", usuarioEntity.getNome());
            dadosEmail.put("mensagem", "Suas informações foram atualizadas com sucesso");
            dadosEmail.put("email", usuarioEntity.getEmail());

            emailService.sendEmail(dadosEmail, "Informações Atualizadas", usuarioEntity.getEmail());

            return usuarioEntity;
        } catch (Exception e) {
            throw new Exception("Erro ao editar o usuário: " + e.getMessage(), e);
        }
    }

    public void removerUsuario(int usuarioId) throws Exception {
        try {
            Usuario usuarioRemovido = usuarioRepository.getById(usuarioId);

            Integer loggedUserId = getIdLoggedUser();

            if (loggedUserId.equals(usuarioId) || isAdmin()) {
                usuarioRemovido.setTipoAtivo(TipoAtivo.N);
                usuarioRepository.save(usuarioRemovido);

                Map<String, Object> dadosEmail = new HashMap<>();
                dadosEmail.put("nomeUsuario", usuarioRemovido.getNome());
                dadosEmail.put("mensagem", "Atenção! Seu usuário foi removido do nosso serviço");
                dadosEmail.put("email", usuarioRemovido.getEmail());

                emailService.sendEmail(dadosEmail, "Usuário Removido", usuarioRemovido.getEmail());
            } else {
                throw new RegraDeNegocioException("Apenas o usuário dono da conta ou um administrador pode remover o usuário.");
            }
        } catch (Exception e) {
            throw new Exception("Erro ao remover o usuário: " + e.getMessage(), e);
        }
    }



    public UsuarioDTO findByEmail(String email) throws RegraDeNegocioException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado: " + email));
        return new UsuarioDTO(usuario);
    }

    public UsuarioDTO findByCpf(String cpf) throws RegraDeNegocioException {
        Usuario usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado: " + cpf));
        return new UsuarioDTO(usuario);
    }


    public Optional<Usuario> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    public Usuario findByLoginAndEmail(String login, String email) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findByLoginAndEmail(login, email);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        throw new Exception("Usuário não encontrado");
    }

    public Usuario findById(Integer idUsuario) throws Exception {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        if (usuario.isPresent()) {
            return usuario.get();
        }
        throw new Exception("Usuário não encontrado");
    }

    public Optional<Usuario> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }


    public UsuarioDTO criarUsuario(UsuarioCreateDTO usuarioCreateDTO) throws Exception {
        String senhaCriptografada = passwordEncoder.encode(usuarioCreateDTO.getSenha());
        Set<Cargo> cargos = new HashSet<>();
        Cargo cargoEntity = cargoRepository.findByNome("ROLE_USUARIO");
        cargos.add(cargoEntity);

        Usuario usuario = objectMapper.convertValue(usuarioCreateDTO, Usuario.class);

        usuario.setSenha(senhaCriptografada);

        Carteira carteira = new Carteira(usuario);

        usuario.setCarteira(carteira);
        usuario.setCargos(cargos);

        usuario = criarUsuario(usuario);

        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
        return usuarioDTO;
    }


    public void salvarUsuario(Usuario usuario) {
        this.usuarioRepository.save(usuario);
    }

}


