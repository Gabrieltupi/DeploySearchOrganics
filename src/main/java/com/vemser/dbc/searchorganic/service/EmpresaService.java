package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.empresa.CreateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaDTO;
import com.vemser.dbc.searchorganic.dto.empresa.EmpresaProdutosDTO;
import com.vemser.dbc.searchorganic.dto.empresa.UpdateEmpresaDTO;
import com.vemser.dbc.searchorganic.dto.pedido.PedidoDTO;
import com.vemser.dbc.searchorganic.exceptions.RegraDeNegocioException;
import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Empresa;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.repository.CargoRepository;
import com.vemser.dbc.searchorganic.repository.EmpresaRepository;
import com.vemser.dbc.searchorganic.service.interfaces.IEmpresaService;
import com.vemser.dbc.searchorganic.utils.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmpresaService implements IEmpresaService {
    private final EmpresaRepository empresaRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioService usuarioService;
    private final CargoRepository cargoRepository;

    public Page<EmpresaDTO> findAll(Pageable pageable) throws Exception {
        Page<Empresa> empresas = empresaRepository.findAll(pageable);
        return empresas.map(this::retornarDto);
    }

    public Usuario getLoggedUser() throws Exception {
        Integer userId = getIdLoggedUser();
        return usuarioService.findById(userId);
    }
    private boolean hasRoleEmpresa(Usuario usuario) {
        Set<Cargo> cargos = usuario.getCargos();
        for (Cargo cargo : cargos) {
            if ("ROLE_EMPRESA".equals(cargo.getNome())) {
                return true;
            }
        }
        return false;
    }
    public EmpresaDTO buscaIdEmpresa(Integer id) throws Exception {
            Usuario usuario= getLoggedUser();
            if (hasRoleEmpresa(usuario)) {
                return retornarDto(empresaRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("empresa nao encontrada")));
            }
            return null;
    }

    public EmpresaDTO findById(Integer idEmpresa) throws Exception {
        if(isUsuario()||isAdmin()) {
            return retornarDto(empresaRepository.findById(idEmpresa).orElseThrow(() -> new RegraDeNegocioException("Empresa não encontrada")));
        } else{
            return buscaIdEmpresa(idEmpresa);
        }
    }


    public boolean isAdmin() {
        Integer userId = getIdLoggedUser();
        Integer count = empresaRepository.existsAdminCargoByUserId(userId);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isUsuario() {
        Integer userId = getIdLoggedUser();
        Integer count = empresaRepository.existsEmpresaCargoByUserId(userId);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }



    public Integer getIdLoggedUser() {
        return Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }




    public Empresa getById(Integer idEmpresa) throws Exception {
        return empresaRepository.findById(idEmpresa).orElseThrow(() -> new RegraDeNegocioException("Empresa não encontrada"));
    }



    public EmpresaDTO save(Integer idUsuario, CreateEmpresaDTO empresaDto) throws Exception {
        Empresa empresa = objectMapper.convertValue(empresaDto, Empresa.class);
        Usuario usuario = usuarioService.obterUsuarioPorId(idUsuario);
        Cargo cargoEntity = cargoRepository.findByNome("ROLE_EMPRESA");
        usuario.getCargos().add(cargoEntity);
        usuarioService.salvarUsuario(usuario);
        empresa.setIdUsuario(idUsuario);

        return retornarDto(empresaRepository.save(empresa));
    }

    public EmpresaDTO update(Integer idEmpresa, UpdateEmpresaDTO empresaDto) throws Exception {
        Usuario usuario= getLoggedUser();
        if(hasRoleEmpresa(usuario) & getIdLoggedUser().equals(empresaDto.getIdUsuario())|| isAdmin()) {
            Empresa empresa = objectMapper.convertValue(empresaDto, Empresa.class);
            empresa.setIdEmpresa(idEmpresa);

            return retornarDto(empresaRepository.save(empresa));
        }
        throw new RegraDeNegocioException("voce só pode atualizar sea propria empresa");
    }

    public void delete(Integer idEmpresa) throws Exception {
        EmpresaDTO empresa=findById(idEmpresa);
        Integer loggedUserId = getIdLoggedUser();

        if (empresa.getIdUsuario().equals(loggedUserId) || isAdmin()) {
            findById(idEmpresa);
        empresaRepository.deleteById(idEmpresa);

        } else {
            throw new RegraDeNegocioException("Apenas o usuário dono da conta ou um administrador pode remover a empresa.");
        }

    }

    public Page<EmpresaProdutosDTO> findAllWithProdutos(Pageable pageable) throws Exception {
        Page<Empresa> empresas = empresaRepository.findAllWithProdutos(pageable);
        return empresas.map(empresa -> objectMapper.convertValue(empresa, EmpresaProdutosDTO.class));
    }

    public EmpresaProdutosDTO findByIdWithProdutos(Integer idEmpresa) throws Exception {
        findById(idEmpresa);
        Optional<Empresa> empresa = empresaRepository.findByIdWithProdutos(idEmpresa);
        return objectMapper.convertValue(empresa, EmpresaProdutosDTO.class);
    }

    private EmpresaDTO retornarDto(Empresa entity) {
        return objectMapper.convertValue(entity, EmpresaDTO.class);
    }
}

