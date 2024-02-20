package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.dto.carteira.CarteiraDTO;
import com.vemser.dbc.searchorganic.dto.usuario.CargoDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioCreateDTO;
import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Carteira;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MockUsuario {
    public static Usuario retornarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setSenha("1234");
        usuario.setCarteira(carteira(usuario));
        usuario.setTipoAtivo(TipoAtivo.S);
        usuario.setNome("Usuario 1");
        usuario.setLogin("usu1");
        usuario.setCpf("12345678900");
        usuario.setEmail("email@example.com");
        usuario.setDataNascimento(LocalDate.parse("1990-05-05"));
        usuario.setSobrenome("Sobrenome do usu 1 ");

        Set<Cargo> cargos = new HashSet<>();
        cargos.add(obterCargo("ROLE_USUARIO"));

        usuario.setCargos(cargos);

        return usuario;
    }

    public static UsuarioDTO retornarUsuarioDTO(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setIdUsuario(usuario.getIdUsuario());
        usuarioDTO.setTipoAtivo(usuario.getTipoAtivo());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setDataNascimento(usuario.getDataNascimento());
        usuarioDTO.setSobrenome(usuario.getSobrenome());
        usuarioDTO.setLogin(usuario.getLogin());
        usuarioDTO.setCargos(retornarCargosDTO(usuario.getCargos()));
        usuarioDTO.setCarteira(carteiraDTO(usuario.getCarteira()));

        return usuarioDTO;
    }

    public static List<Usuario> retornarUsuarios(Usuario usuario) {
        return List.of(usuario, usuario, usuario);
    }

    public static UsuarioCreateDTO retornarUsuarioCreateDTO(Usuario usuario) {
        UsuarioCreateDTO usuarioDTO = new UsuarioCreateDTO();
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setTipoAtivo(usuario.getTipoAtivo());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setCpf(usuario.getCpf());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setDataNascimento(usuario.getDataNascimento());
        usuarioDTO.setSobrenome(usuario.getSobrenome());

        return usuarioDTO;
    }

    public static Cargo obterCargo(String role){
        Cargo cargo = new Cargo();
        cargo.setIdCargo(new Random().nextInt());
        cargo.setNome(role);

        return cargo;
    }

    public static Set<CargoDTO> retornarCargosDTO(Set<Cargo> cargos){
        Set<CargoDTO> cargosDTO = new HashSet<>();

        cargos.forEach((cargo) -> {
            CargoDTO cargoDTO = new CargoDTO();
            cargo.setIdCargo(cargo.getIdCargo());
            cargo.setNome(cargo.getNome());

            cargosDTO.add(cargoDTO);
        });

        return cargosDTO;
    }

    public static Carteira carteira(Usuario usuario){
        Carteira carteira = new Carteira();
        carteira.setSaldo(BigDecimal.valueOf(0));
        carteira.setIdCarteira(1);
        carteira.setUsuario(usuario);

        return carteira;
    }

    public static CarteiraDTO carteiraDTO(Carteira carteira){
        CarteiraDTO carteiraDTO = new CarteiraDTO();
        carteiraDTO.setSaldo(carteira.getSaldo());
        carteiraDTO.setIdCarteira(carteira.getIdCarteira());

        return carteiraDTO;
    }

    public static Usuario retornarUsuarioAdmin() {
        Usuario admin = retornarUsuario();
        admin.setIdUsuario(retornarUsuario().getIdUsuario());
        admin.setNome("Admin");
        admin.getCargos().add(obterCargo("ROLE_ADMIN"));

        return admin;
    }

    public static Usuario retornarUsuarioUsuario() {
        Usuario admin = retornarUsuario();
        admin.setIdUsuario(retornarUsuario().getIdUsuario());
        admin.setNome("Usuario 1");
        admin.getCargos().add(obterCargo("ROLE_USUARIO"));

        return admin;
    }
}
