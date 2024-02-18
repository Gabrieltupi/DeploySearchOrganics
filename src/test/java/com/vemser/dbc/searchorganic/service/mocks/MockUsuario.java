package com.vemser.dbc.searchorganic.service.mocks;

import com.vemser.dbc.searchorganic.model.Cargo;
import com.vemser.dbc.searchorganic.model.Carteira;
import com.vemser.dbc.searchorganic.model.Usuario;
import com.vemser.dbc.searchorganic.utils.TipoAtivo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MockUsuario {
    public static Usuario retornarUsuario(){
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(new Random().nextInt());
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

    public static Cargo obterCargo(String role){
        Cargo cargo = new Cargo();
        cargo.setIdCargo(new Random().nextInt());
        cargo.setNome(role);
        return cargo;
    }

    public static Set<Cargo> obterTodosCargos(){
        Set<Cargo> cargos = new HashSet<>();
        cargos.add(obterCargo("ROLE_ADMIN"));
        cargos.add(obterCargo("ROLE_EMPRESA"));
        cargos.add(obterCargo("ROLE_USUARIO"));
        return obterTodosCargos();

    }
    public static Carteira carteira(Usuario usuario){
        Carteira carteira = new Carteira();
        carteira.setSaldo(BigDecimal.valueOf(0));
        carteira.setIdCarteira(1);
        carteira.setUsuario(usuario);

        return carteira;
    }
}
